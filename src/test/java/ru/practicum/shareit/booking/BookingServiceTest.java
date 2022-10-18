package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
class BookingServiceTest extends ShareItTests {

    private final BookingRepository bookingRepository;

    private final BookingService bookingService;

    private final ItemService itemService;
    private final UserService userService;
    private User user = new User(1L, "Simple User", "user@mail.ru");
    private User user2 = new User(2L, "Another User", "test@mail.ru");
    private Item item = new Item(1L, "Unit", "Super unit", true, user, null);
    private Booking booking = new Booking(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(2),
            item, user2, BookingStatus.WAITING, false, false);

    @Autowired
    public BookingServiceTest(BookingRepository bookingRepository,
                              BookingService bookingService,
                              ItemService itemService,
                              UserService userService) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        this.itemService = itemService;
        this.userService = userService;
        userService.add(UserMapper.toUserDto(user));
        userService.add(UserMapper.toUserDto(user2));
        itemService.addNewItem(1L, ItemMapper.toItemDto(item));
        bookingRepository.save(booking);
    }

    @Test
    void add() {
        bookingRepository.save(booking);
        List<Booking> all = bookingRepository.findAll();
        System.out.println(all.size());
//        BookingDto externalBookingDto = BookingDto.builder()
//                .start(LocalDateTime.now().plusMinutes(1))
//                .end(LocalDateTime.now().plusDays(2))
//                .booker(UserMapper.toUserDto(user))
//                .item(ItemMapper.toItemDto(item))
//                .itemId(1L)
//                .build();
//        System.out.println(externalBookingDto);
//        Booking booking1 = BookingMapper.toNewBooking(externalBookingDto);
//        booking1.setId(1L);
//        System.out.println(booking1);
//        assertEquals(booking1, bookingRepository.findById(booking1.getId()).orElse(null));
    }

    @Test
    void upgrade() {
    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getAllOfOwner() {
    }
}