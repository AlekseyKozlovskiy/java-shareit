package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exceptions.IncorrectRequest;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.LastBooking;
import ru.practicum.shareit.item.NextBooking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookingServiceTest extends ShareItTests {

    private final BookingService bookingService;
    private final BookingRepository repository;
    private final UserService userService;
    private final ItemService itemService;

    @Autowired
    BookingServiceTest(BookingService bookingService, BookingRepository repository, UserService userService,
                       ItemService itemService) {
        this.bookingService = bookingService;
        this.repository = repository;
        this.userService = userService;
        this.itemService = itemService;
    }

    private BookingDto newBookingDto;
    private UserDto owner;
    private UserDto booker;
    private ItemDto itemDto;

    @BeforeEach
    void setUp() {

        owner = userService.add(UserDto.builder().name("user1").email("test1@test1.com").build());
        booker = userService.add(UserDto.builder().name("user2").email("user2@test1.com").build());
        itemDto = itemService.addNewItem(owner.getId(),
                ItemDto.builder().id(1L).name("item1").description("item1").available(true).build());
        newBookingDto = BookingDto.builder()
                .id(1L)
                .itemId(1L)
                .start(LocalDateTime.now().plusMinutes(1))
                .end(LocalDateTime.now().plusMinutes(2))
                .booker(booker)
                .status(BookingStatus.WAITING)
                .approved(false)
                .item(itemDto)
                .canceled(false)
                .build();
    }

    @Test
    void add() {
        bookingService.add(booker.getId(), newBookingDto);
        assertNotNull(repository.findById(newBookingDto.getId()).orElseThrow());
        assertEquals(repository.findById(newBookingDto.getId()).orElseThrow().getEnd(), newBookingDto.getEnd());
    }

    @Test
    void addNewBookingInvalid() {
        itemDto.setAvailable(false);
        itemService.upgradeItem(owner.getId(), itemDto, itemDto.getId());
        assertThrows(ValidationException.class, () ->
                bookingService.add(booker.getId(), newBookingDto));
        assertThrows(ValidationException.class, () -> bookingService.add(
                booker.getId(), newBookingDto.toBuilder().start(LocalDateTime.now().minusDays(2)).build()));
        assertThrows(ValidationException.class, () -> bookingService.add(
                booker.getId(), newBookingDto.toBuilder().start(LocalDateTime.now().plusDays(2))
                        .end(LocalDateTime.now().plusDays(1)).build()));
        assertThrows(IncorrectRequest.class, () -> bookingService.add(10L, newBookingDto));
        assertThrows(IncorrectRequest.class, () -> bookingService.add(
                booker.getId(), newBookingDto.toBuilder().itemId(5L).build()));
        itemService.upgradeItem(owner.getId(), itemDto.toBuilder().available(false).build(), 1L);
        assertThrows(ValidationException.class, () -> bookingService.add(booker.getId(), newBookingDto));
    }

    @Test
    void upgrade() {
        BookingDto bookingDto1 = bookingService.add(booker.getId(), newBookingDto);
        BookingDto updateBookingDto = bookingService.upgrade(owner.getId(), bookingDto1.getId(), true);
        assertEquals(BookingStatus.APPROVED, updateBookingDto.getStatus());
    }

    @Test
    void updateBookingInvalid() {
        bookingService.add(booker.getId(), newBookingDto);
        assertThrows(ValidationException.class, () -> bookingService.upgrade(owner.getId(), newBookingDto.getId(),
                null));
        assertThrows(EntityNotFoundException.class, () -> bookingService.upgrade(owner.getId(), 5L, true));
        assertThrows(IncorrectRequest.class, () -> bookingService.upgrade(5L,
                newBookingDto.getId(), true));
    }

    @Test
    void get() {
        BookingDto bookingDto1 = bookingService.add(booker.getId(), newBookingDto);
        BookingDto bookingDto2 = bookingService.get(owner.getId(), newBookingDto.getId(), 1L, 10L);
        assertEquals(bookingDto1, bookingDto2);
    }

    @Test
    void findBookingInvalid() {
        assertThrows(IncorrectRequest.class, () -> bookingService.get(5L, newBookingDto.getId(), 1L, 10L));
        assertThrows(IncorrectRequest.class, () -> bookingService.get(booker.getId(), 5L, 1L, 10L));
    }

    @Test
    void getAllFromUserByFutureState() {
        final BookingDto bookingDto1 = bookingService.add(booker.getId(), newBookingDto);
        assertEquals(List.of(bookingDto1), bookingService.getAll(booker.getId(), "FUTURE", PageRequest.ofSize(10)));
    }

    @Test
    void getAllFromUserByWaitingState() {
        final BookingDto bookingDto1 = bookingService.add(booker.getId(), newBookingDto);
        bookingDto1.setStatus(BookingStatus.WAITING);
        assertEquals(List.of(bookingDto1), bookingService.getAll(booker.getId(), "WAITING", PageRequest.ofSize(10)));
    }

    @Test
    void getAllFromUserByRejectedState() {
        BookingDto bookingDto1 = bookingService.add(booker.getId(), newBookingDto);
        BookingDto updateBookingDto1 = bookingService.upgrade(owner.getId(), bookingDto1.getId(), false);
        assertEquals(1, bookingService.getAll(booker.getId(), "REJECTED", PageRequest.ofSize(10)).size());
    }

    @Test
    void getAllOfOwnerWithDifferentState() {
        BookingDto bookingDto1 = bookingService.add(booker.getId(), newBookingDto);
        assertEquals(List.of(bookingDto1), bookingService.getAllOfOwner(owner.getId(), "ALL", PageRequest.ofSize(10)));
        bookingDto1.setStatus(BookingStatus.WAITING);
        assertEquals(List.of(bookingDto1), bookingService.getAllOfOwner(owner.getId(), "WAITING", PageRequest.ofSize(10)));
        bookingService.upgrade(owner.getId(), bookingDto1.getId(), false);
        assertEquals(1, bookingService.getAllOfOwner(owner.getId(), "REJECTED", PageRequest.ofSize(10)).size());
    }

    @Test
    void lastAndNextBookingTest() throws InterruptedException {
        LastBooking lastBooking = bookingService.lastBooking(1L);
        NextBooking nextBooking = bookingService.nextBooking(1L);
        assertEquals(lastBooking, bookingService.lastBooking(1L));
        assertEquals(nextBooking, bookingService.nextBooking(1L));

        newBookingDto.setStart(LocalDateTime.now().minusDays(1).withNano(0));
        newBookingDto.setEnd(LocalDateTime.now().minusHours(1).withNano(0));
        repository.save(BookingMapper.toNewBooking(newBookingDto));
        assertEquals(1, bookingService.lastBooking(1L).getId());

        newBookingDto.setStart(LocalDateTime.now().plusDays(1).withNano(0));
        newBookingDto.setEnd(LocalDateTime.now().plusDays(2).withNano(0));
        repository.save(BookingMapper.toNewBooking(newBookingDto));
        assertEquals(2, bookingService.nextBooking(1L).getId());

    }


    @Test
    void getAll() {
        BookingDto bookingDto1 = bookingService.add(booker.getId(), newBookingDto);
        BookingDto bookingDto2 = bookingService.add(booker.getId(), newBookingDto);
        List<BookingDto> all = bookingService.getAll(booker.getId(), "ALL", PageRequest.ofSize(10));
        assertEquals(2, all.size());
    }

    @Test
    void getAllOfOwner() {
        BookingDto bookingDto1 = bookingService.add(booker.getId(), newBookingDto);
        BookingDto bookingDto2 = bookingService.add(booker.getId(), newBookingDto);
        List<BookingDto> all = bookingService.getAllOfOwner(1L, "ALL", PageRequest.ofSize(10));
        assertEquals(2, all.size());
    }

    @Test
    void getAllForItemsInvalid() {
        assertThrows(IncorrectRequest.class, () -> bookingService.getAll(5L, "ALL", PageRequest.ofSize(10)));
        assertThrows(ValidationException.class, () -> bookingService.getAll(owner.getId(), "BLA", PageRequest.ofSize(10)));
    }

    @Test
    void tryToBookingSelfItem() {
        newBookingDto = BookingDto.builder()
                .id(1L)
                .itemId(1L)
                .start(LocalDateTime.now().plusMinutes(1))
                .end(LocalDateTime.now().plusMinutes(2))
                .booker(owner)
                .status(BookingStatus.WAITING)
                .approved(false)
                .item(itemDto)
                .canceled(false)
                .build();
        assertThrows(IncorrectRequest.class, () -> bookingService.add(owner.getId(), newBookingDto));
    }

    @Test
    void getAllFromUserByCurrentState() {
        User user = new User(2L, "name", "email@email.com");
        newBookingDto.setStart(LocalDateTime.now().minusDays(1).withNano(0));
        newBookingDto.setEnd(LocalDateTime.now().plusDays(1).withNano(0));
        repository.save(BookingMapper.toNewBooking(newBookingDto));
        assertNotNull(bookingService.get(user.getId(), 1L, 1L, 10L));
        assertEquals(1, bookingService.getAll(user.getId(), "CURRENT", PageRequest.ofSize(10)).size());
    }
}