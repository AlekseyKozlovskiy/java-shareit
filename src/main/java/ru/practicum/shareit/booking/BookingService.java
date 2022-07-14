package ru.practicum.shareit.booking;

public interface BookingService {
    Booking add(Long userId, Long itemId, Booking booking);
}
