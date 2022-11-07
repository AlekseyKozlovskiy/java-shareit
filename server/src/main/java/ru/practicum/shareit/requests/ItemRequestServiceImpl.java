package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.util.ItemRequestValidation;
import ru.practicum.shareit.util.UserValidation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserValidation userValidation;
    private final UserRepository userRepository;
    private final ItemRequestValidation itemRequestValidation;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto add(Long userId, ItemRequestDto itemRequestDto) {
        userValidation.isUserRegister(userId);

        itemRequestDto.setCreated(LocalDateTime.now());
        itemRequestDto.setRequestor(UserMapper.toUserDto(userRepository.getById(userId)));
        ItemRequest itemRequest = ItemRequestMapper.toNewItemRequest(itemRequestDto);
        itemRequestRepository.save(itemRequest);
        itemRequestDto.setId(itemRequest.getId());
        List<Item> all = itemRepository.findAll();
        List<Item> list = new ArrayList<>();
        for (Item item : all) {
            String stroka = item.getName().replaceAll(" ", "").toLowerCase().substring(0, 3);
            String zapros = itemRequestDto.getDescription().replaceAll(" ", "").toLowerCase();
            if (zapros.contains(stroka)) {
                list.add(item);
            }
        }
        itemRequestDto.setItems(ItemMapper.toItemDtoList(list));

        return itemRequestDto;
    }

    @Override
    public List<ItemRequestDto> get(Long userId) {
        userValidation.isUserRegister(userId);
        List<ItemDto> all = ItemMapper.toItemDtoList(itemRepository.findAll());

        List<ItemRequestDto> allByRequesterId = ItemRequestMapper.itemRequestDtoList(itemRequestRepository.getAllByRequester_Id(userId));
        for (ItemRequestDto itemRequest : allByRequesterId) {
            List<ItemDto> list = new ArrayList<>();
            for (ItemDto item : all) {
                String stroka = item.getName().replaceAll(" ", "").toLowerCase().substring(0, 3);
                String zapros = itemRequest.getDescription().replaceAll(" ", "").toLowerCase();
                if (zapros.contains(stroka)) {
                    list.add(item);
                    item.setRequestId(itemRequest.getId());
                }
            }
            itemRequest.setItems(list);
        }
        return allByRequesterId;
    }

    @Override
    public List<ItemRequestDto> getAll(Long userId, Long from, Long size) {
        List<ItemDto> all = ItemMapper.toItemDtoList(itemRepository.findAll());
        List<ItemRequestDto> i = ItemRequestMapper.itemRequestDtoList(itemRequestRepository.findAll());
        List<ItemRequestDto> test = new ArrayList<>();
        for (ItemRequestDto itemRequest : i) {
            String zapros = itemRequest.getDescription().replaceAll(" ", "").toLowerCase();
            List<ItemDto> list = new ArrayList<>();
            for (ItemDto item : all) {
                String stroka = item.getName().replaceAll(" ", "").toLowerCase().substring(0, 3);
                if (zapros.contains(stroka)) {
                    list.add(item);
                    item.setRequestId(itemRequest.getId());
                }
            }
            itemRequest.setItems(list);
        }
        for (ItemRequestDto itemRequestDto : i) {
            for (ItemDto item : itemRequestDto.getItems()) {
                if (Objects.equals(item.getOwner().getId(), userId)) {
                    test.add(itemRequestDto);
                }
            }
        }

        return test;
    }

    @Override
    public ItemRequestDto getById(Long userId, Long itemRequestId) {
        userValidation.isUserRegister(userId);
        itemRequestValidation.isItemRequestExists(itemRequestId);
        ItemRequestDto byId = ItemRequestMapper.toItemRequestDto(itemRequestRepository.getById(itemRequestId));
        List<ItemDto> all = ItemMapper.toItemDtoList(itemRepository.findAll());
        for (ItemDto itemDto : all) {
            List<ItemDto> list = new ArrayList<>();
            String stroka = itemDto.getName().replaceAll(" ", "").toLowerCase().substring(0, 3);
            String zapros = byId.getDescription().replaceAll(" ", "").toLowerCase();
            if (zapros.contains(stroka)) {
                list.add(itemDto);
                itemDto.setRequestId(byId.getId());
            }
            byId.setItems(list);
        }

        return byId;
    }
}
