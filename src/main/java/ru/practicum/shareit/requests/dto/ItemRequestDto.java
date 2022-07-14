package ru.practicum.shareit.requests.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDate;

@Builder
@Data
public class ItemRequestDto {
    private Long id;
    private String description;
    private UserDto requestor;
    private LocalDate created;
}
