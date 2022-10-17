package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private Long itemId;
    private LocalDateTime start;
    private LocalDateTime end;


    private UserDto booker;
    private BookingStatus status;
    private Boolean approved;
    private ItemDto item;
    private Boolean canceled;


}
