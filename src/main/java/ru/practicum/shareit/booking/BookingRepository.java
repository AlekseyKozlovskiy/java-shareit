package ru.practicum.shareit.booking;

public interface BookingRepository {
    Booking add(Long userId, Long itemId, Booking booking);
}
