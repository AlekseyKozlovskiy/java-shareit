package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.comments.CommentDtoNew;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.requests.ItemRequestService;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.util.BookingValidation;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class ItemServiceTest extends ShareItTests {

    private final UserService userService;

    private final ItemService itemService;

    private final BookingService bookingService;

    private final ItemRequestService itemRequestService;

    @Autowired
    ItemServiceTest(UserService userService, ItemService itemService, BookingService bookingService,
                    ItemRequestService itemRequestService, BookingValidation bookingValidation) {
        this.userService = userService;
        this.itemService = itemService;
        this.bookingService = bookingService;
        this.itemRequestService = itemRequestService;
    }

    private UserDto user1;
    private UserDto user2;
    private ItemDto item1;
    private ItemDto item2;
    private BookingDto booking;

    @BeforeEach
    void setUp() {
        user1 = UserDto.builder().name("test1").email("test1@test1.com").build();
        user2 = UserDto.builder().name("user2").email("user2@test1.com").build();
        item1 = ItemDto.builder().id(1L).name("item1").description("item1").available(true).build();
        item2 = ItemDto.builder().name("item2").description("item2").available(true).build();
        booking = BookingDto.builder()
                .id(1L)
                .itemId(item1.getId())
                .start(LocalDateTime.now().plusSeconds(1))
                .end(LocalDateTime.now().plusSeconds(2))
                .build();
    }

    @Test
    void contextLoads() {
        assertNotNull(itemService);
    }

    @Test
    void addNewItem() {
        UserDto userDto = userService.add(user1);
        ItemDto itemDto = itemService.addNewItem(userDto.getId(), item1);
        assertEquals(1, itemService.get(userDto.getId(), itemDto.getId()).getId());
    }

    @Test
    void upgradeItem() {
        UserDto userDto = userService.add(user1);
        ItemDto itemDto = itemService.addNewItem(userDto.getId(), item1);
        itemDto.setDescription("New Desc");
        itemService.upgradeItem(userDto.getId(), itemDto, itemDto.getId());
        assertEquals("New Desc", itemService.get(1L, 1L).getDescription());
    }

    @Test
    void get() {
        UserDto userDto = userService.add(user1);
        ItemDto itemDto = itemService.addNewItem(userDto.getId(), item1);
        assertEquals(itemDto.getId(), itemService.get(userDto.getId(), itemDto.getId()).getId());
    }

    @Test
    void getAll() {
        final UserDto userDto = userService.add(user1);
        final ItemDto itemDto = itemService.addNewItem(userDto.getId(), item1);
        final ItemDto itemDto2 = itemService.addNewItem(userDto.getId(), item2);
        assertEquals(2, itemService.getAll(userDto.getId()).size());
    }

    @Test
    void search() {
        UserDto userDto = userService.add(user1);
        ItemDto itemDto = itemService.addNewItem(userDto.getId(), item1);
        assertEquals(List.of(item1), itemService.search("em1"));
    }

    @Test
    void addComment() {
        final UserDto owner = userService.add(user1);
        final UserDto booker = userService.add(user2);
        final ItemDto itemDto = itemService.addNewItem(owner.getId(), item1);
        bookingService.add(booker.getId(), booking);
        CommentDtoNew commentDto = CommentDtoNew.builder().id(1L).text("text").build();
        itemService.addComment(booker.getId(), commentDto, itemDto.getId());
        ItemDto itemDto1 = itemService.get(owner.getId(), itemDto.getId());
        assertEquals(1, itemDto1.getComments().size());
    }
}