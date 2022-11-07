package ru.practicum.shareit.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Valid
public class CommentDtoNew {
    private Long id;
    @NotBlank
    private String text;
    private ItemDto item;
    private UserDto user;
    private LocalDateTime created;
    private String authorName;
}
