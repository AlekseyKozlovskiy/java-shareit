package ru.practicum.shareit.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class CommentDtoNew {
    private Long id;
    private String text;
    private ItemDto item;
    private UserDto user;
    private LocalDateTime created;
    private String authorName;
}
