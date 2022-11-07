package ru.practicum.shareit.requests;

import ru.practicum.shareit.requests.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto add(Long userId, ItemRequestDto itemRequestDto);

    List<ItemRequestDto> get(Long userId);

    List<ItemRequestDto> getAll(Long userId, Long from, Long size);

    ItemRequestDto getById(Long userId, Long itemRequestId);

}
