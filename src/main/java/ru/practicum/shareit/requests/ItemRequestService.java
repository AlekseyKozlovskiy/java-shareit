package ru.practicum.shareit.requests;

public interface ItemRequestService {
    ItemRequest add(Long userId, ItemRequest itemRequest);
    void delete(Long userId, Long requestId);
}
