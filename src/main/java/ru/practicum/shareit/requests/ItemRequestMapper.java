package ru.practicum.shareit.requests;

import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserMapper;

public class ItemRequestMapper {
    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return ItemRequestDto.builder()
                .description(itemRequest.getDescription())
                .requestor(UserMapper.toUserDto(itemRequest.getRequester()))
                .created(itemRequest.getCreated())
                .build();
    }

    public static ItemRequest toNewItemRequest(ItemRequestDto itemRequestDto) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setRequester(UserMapper.toNewUser(itemRequestDto.getRequestor()));
        itemRequest.setCreated(itemRequestDto.getCreated());
        return itemRequest;
    }
}
