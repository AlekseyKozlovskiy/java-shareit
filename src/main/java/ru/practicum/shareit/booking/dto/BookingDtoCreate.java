package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;

@Builder
@Data
@Valid
public class BookingDtoCreate {
    private Long id;
}
