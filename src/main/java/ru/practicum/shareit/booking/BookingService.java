package ru.practicum.shareit.booking;

import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto add(Long userId, BookingDto bookingDto);

    BookingDto upgrade(Long userId, Long bookingId, Boolean bookingStatus);

    BookingDto get(Long userId, Long bookingId, Long from, Long size);

    List<BookingDto> getAll(Long userId, String state, PageRequest pageRequest);

    List<BookingDto> getAllOfOwner(Long userId, String state, PageRequest pageRequest);
}
