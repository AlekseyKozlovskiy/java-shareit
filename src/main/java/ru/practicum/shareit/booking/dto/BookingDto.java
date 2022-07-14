package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDate;

@Builder
public class BookingDto {
    private Long id;
    private LocalDate start;
    private LocalDate end;
    private ItemDto item;
    private UserDto booker;
    private BookingStatus bookingStatus;
}
