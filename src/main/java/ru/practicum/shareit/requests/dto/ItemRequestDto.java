package ru.practicum.shareit.requests.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Setter
@Getter
@Valid
public class ItemRequestDto {
    private Long id;
    private String description;
    private UserDto requestor;
    private LocalDateTime created;
    private List<ItemDto> items;

    @Override
    public String toString() {
        return "ItemRequestDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", requestor=" + requestor +
                ", created=" + created +
                '}';
    }
}


