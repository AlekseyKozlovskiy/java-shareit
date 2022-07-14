package ru.practicum.shareit.requests;

import ru.practicum.shareit.requests.dto.ItemRequestDto;

public interface ItemRequestService {
    ItemRequestDto add(Long userId, ItemRequestDto itemRequestDto);

    void delete(Long userId, Long requestId);
}
