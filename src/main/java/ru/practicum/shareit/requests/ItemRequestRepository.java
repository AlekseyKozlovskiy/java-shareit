package ru.practicum.shareit.requests;

public interface ItemRequestRepository {
    ItemRequest add(Long userId, ItemRequest itemRequest);

    void delete(Long userId, Long requestId);
}
