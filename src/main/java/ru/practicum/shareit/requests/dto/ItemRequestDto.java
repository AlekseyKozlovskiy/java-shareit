package ru.practicum.shareit.requests.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDate;

@Builder
@Setter
@Getter
public class ItemRequestDto {
    private Long id;
    private String description;
    private UserDto requestor;
    private LocalDate created;
}
