package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.comments.CommentRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.util.BookingValidation;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
class ItemServiceTest extends ShareItTests {

    private final CommentRepository commentRepository;

    private final ItemRepository itemRepository;


    private final BookingService bookingService;
    private final ItemService itemService;
    private final UserRepository userRepository;

    private final User user = new User(1L, "Simple User", "user@mail.ru");
    private final User user2 = new User(2L, "Another User", "test@mail.ru");
    private final Item item = new Item(1L, "Unit", "Super unit", true, user, null);
    private final Item item2 = new Item(2L, "New Name", "Super unit", true, user, null);

    private final Item item1;
    private final ItemDto itemDto;
    @MockBean
    BookingValidation bookingValidation;


    @Autowired
    public ItemServiceTest(CommentRepository commentRepository,
                           UserRepository userRepository,
                           ItemRepository itemRepository,
                           BookingService bookingService,
                           ItemService itemService
    ) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        userRepository.save(user);
        userRepository.save(user2);
        this.itemRepository = itemRepository;
        this.itemService = itemService;
        item1 = itemRepository.save(item);
        this.bookingService = bookingService;
        itemDto = ItemMapper.toItemDto(item1);
        itemDto.setComments(new ArrayList<>());
        itemDto.setLastBooking(bookingService.lastBooking(item1.getId()));
        itemDto.setNextBooking(bookingService.nextBooking(item1.getId()));
//        itemDto.setComments(new ArrayList<>());
//        LastBooking lastBooking = new LastBooking();
//        lastBooking.setBookerId(1L);
//        lastBooking.setId(1L);
//        itemDto.setLastBooking(bookingService.lastBooking(item1.getId()));
//        System.out.println("!!!!!!!!!!!!!!!!!" + itemDto.getLastBooking());
//        bookingService.lastBooking(item1.getId());
//        itemDto.setNextBooking(bookingValidation.nextBooking(item1.getId()));

    }


    //    @BeforeEach
//    public void init() {
//        itemDto = ItemMapper.toItemDto(item1);
//        itemDto.setComments(new ArrayList<>());
//        itemDto.setLastBooking(bookingValidation.lastBooking(item1.getId()));
//        itemDto.setNextBooking(bookingValidation.nextBooking(item1.getId()));
//    }
//    UserService userService;
//    UserRepository userRepository;
//    @MockBean
//    ItemService itemService;
//    @Autowired
//    ItemMapper itemMapper;
    //    @Autowired
//    UserRepository userRepository;
//    @MockBean
//    ItemRepository itemRepository;

    @Test
    void addNewItem() {
//        ItemDto itemDto = ItemMapper.toItemDto(item);
//        Mockito.when(itemRepository.save(item)).thenReturn(item);
//        itemService.addNewItem(1L, itemDto);
//        assertEquals(1, itemDto.getId());
        Item item2 = ItemMapper.toNewItem(itemService.addNewItem(1L, ItemMapper.toItemDto(item1)));
        assertEquals(itemRepository.findById(item1.getId()).orElse(null), item2);
    }

    @Test
    void upgradeItem() {
//        ItemDto itemDto = ItemMapper.toItemDto(item);
//        ItemDto itemDto2 = ItemMapper.toItemDto(item2);
//        Mockito.when(itemRepository.save(item)).thenReturn(item);
//        itemService.upgradeItem(1L, itemDto2, 1L);
//        assertEquals(1, itemDto.getId());
//        Mockito.when(itemRepository.findAll()).thenReturn(List);
//        ItemDto itemDto = ItemDtoCreater.getItemDto();
//        Item item = itemMapper.toNewItem(itemDto);
//
//        Mockito.when(itemRepository.save(item)).thenReturn(item);
//        Mockito.when(itemService.upgradeItem(1L, itemDto, 1L)).thenReturn(itemDto);
//        ItemDto add = itemService.upgradeItem(1L, itemDto, 1L);
//        Assertions.assertTrue(add.getId() == 1L);
    }

    @Test
    void get() {
//        System.out.println("@@@@@@@@@@@@@@@@@@" + itemService.get(user.getId(), item1.getId()));
//        assertEquals(itemDto, itemService.get(user.getId(), item1.getId()));
//        assertEquals(itemDto, itemService.get(item1.getId(), user.getId()));
//        itemDto = ItemMapper.toItemDto(item1);
//        itemDto.setComments(new ArrayList<>());
//        itemDto.setLastBooking(bookingValidation.lastBooking(item1.getId()));
//        itemDto.setNextBooking(bookingValidation.nextBooking(item1.getId()));
//        bookingValidation.lastBooking(item1.getId());
//        System.out.println(itemDto);
//
//        assertEquals(itemDto, itemService.get(item1.getId(), user.getId()));
//        ItemDto itemDto = ItemDtoCreater.getItemDto();
//        Item item = itemMapper.toNewItem(itemDto);
//        Mockito.when(itemRepository.getById(1L)).thenReturn(item);
//        ItemDto itemDto1 = itemService.get(1L, 1L);
//        Assertions.assertEquals(itemDto, itemDto1);

//        when(userService.getUserDtoById(user.getId())).thenReturn(user);
//        mvc.perform(get("/users/{id}", user.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(notNullValue())))
//                .andExpect(jsonPath("$.name", is(user.getName())))
//                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    void getAll() {
//        System.out.println(List.of(itemDto));
//        System.out.println(itemService.getAll(1L));
        assertEquals(1, List.of(itemService.getAll(1L)).size());
//        assertEquals(List.of(itemDto), itemService.getAll(1L));
//        assertEquals(List.of(itemDto), itemService.getAll(1L));
//        final List<Item> userPage = new ArrayList<>(Collections.singletonList(item));
//        when(itemRepository.findAll())
//                .thenReturn(userPage);
//
//        final List<ItemDto> userDtos = itemService.getAll(1L);
//        System.out.println(userPage);
//        System.out.println(userDtos);
//        assertNotNull(userDtos);
//        assertEquals(1, userDtos.size());
//        assertEquals(UserMapper.toUserDto(user), userDtos.get(0));
    }

    @Test
    void search() {
        assertEquals(List.of(ItemMapper.toItemDto(item1)), itemService.search("nit"));
    }

    @Test
    void addComment() throws InterruptedException {
//        System.out.println(itemService.getAll(1L));
//        Comment comment = new Comment();
//        comment.setText("text");
//        comment.setUser(user);
//        comment.setItem(item);
//        comment.setId(1L);
//        CommentDtoNew commentDto = CommentMapper.toCommentDtoNew(comment);
////        Mockito.doNothing().when(bookingValidation).addComment(1L, commentDto, 1L);
////        BookingDto externalBookingDto = BookingDto.builder()
////                .start(LocalDateTime.now().plusSeconds(2))
////                .end(LocalDateTime.now().plusDays(2))
////                .itemId(item1.getId())
////                .build();
////        BookingDto booking = bookingService.add( user.getId(),
////                externalBookingDto);
////        bookingService.add(booking.getId(), externalBookingDto);
////        UserDto userDto = UserMapper.toUserDto(user);
//
////        Thread.sleep(2000);
//        itemService.addComment(1L, commentDto, 1L);
//        System.out.println(itemService.getAll(1L));
//
//        Thread.sleep(2000);
//        CommentDtoNew comment1 = itemService.addComment(
//                user2.getId(),
//                commentDto,
//                item1.getId()
//        );
//        assertEquals(commentRepository.findById(comment1.getId()).orElse(null), comment1);
    }
}