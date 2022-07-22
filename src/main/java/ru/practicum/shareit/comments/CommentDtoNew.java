package ru.practicum.shareit.comments;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Builder
@Data
@Valid
public class CommentDtoNew {
    private Long id;
    private String text;
    private ItemDto item;
    private UserDto user;
    private LocalDateTime created;
    private String authorName;
}
