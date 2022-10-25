package ru.practicum.shareit.requests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
class ItemRequestServiceTest extends ShareItTests {
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRequestService itemRequestService;
    private final ItemRepository itemRepository;
    private final UserService userService;
    private User user = new User(1L, "Simple User", "user@mail.ru");
    private Item item = new Item(1L, "text", "text", true, user, null);
    private ItemRequest itemRequest = new ItemRequest(1L, "text", user, LocalDateTime.now());

    @Autowired
    public ItemRequestServiceTest(
            ItemRequestRepository itemRequestRepository,
            ItemRequestService itemRequestService,
            ItemRepository itemRepository, UserService userService) {
        this.itemRequestRepository = itemRequestRepository;
        this.itemRequestService = itemRequestService;
        this.itemRepository = itemRepository;
        this.userService = userService;
        userService.add(UserMapper.toUserDto(user));
        itemRepository.save(item);
        itemRequestRepository.save(itemRequest);
    }

    @Test
    void add() {
        ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(itemRequest);
        itemRequestDto.setDescription("text");
        ItemRequestDto itemRequest1 = itemRequestService
                .add(itemRequest.getRequester().getId(), itemRequestDto);
        Optional<ItemRequest> byId = itemRequestRepository
                .findById(itemRequest1.getId());
        assertEquals(byId.get().getDescription(), itemRequest1.getDescription());
    }

    @Test
    void get() {
        List<ItemRequestDto> itemRequestDtos = itemRequestService.get(user.getId());
        assertEquals(itemRequestDtos.get(0).getRequestor(), ItemRequestMapper.toItemRequestDto(itemRequest).getRequestor());
    }

    @Test
    void getAll() {
        List<ItemRequestDto> all = itemRequestService.getAll(1L, null, null);
        assertEquals(all.size(), 1);
        List<ItemRequestDto> all2 = itemRequestService.getAll(1L, 0L, 1L);
        assertEquals(all2.size(), 1);
    }

    @Test
    void getById() {
        assertEquals(itemRequestService.getById(user.getId(), itemRequest.getId()
        ).getRequestor(), ItemRequestMapper.toItemRequestDto(itemRequest).getRequestor());
    }
}