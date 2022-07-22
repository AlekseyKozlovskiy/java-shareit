package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.comments.CommentDtoNew;
import ru.practicum.shareit.item.LastBooking;
import ru.practicum.shareit.item.NextBooking;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Data
@Builder
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private UserDto owner;
    private ItemRequestDto itemRequest;
    private LastBooking lastBooking;
    private NextBooking nextBooking;
    private List<CommentDtoNew> comments;
}
