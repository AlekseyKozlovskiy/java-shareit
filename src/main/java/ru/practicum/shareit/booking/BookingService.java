package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;

import java.util.List;

public interface BookingService {
    BookingDtoCreate add(Long userId, BookingDto bookingDto);

    BookingDto upgrade(Long userId, Long bookingId, Boolean bookingStatus);

    BookingDto get(Long userId, Long bookingId);

    List<BookingDto> getAll(Long userId, String state);

    List<BookingDto> getAllOfOwner(Long userId, String state);
}
