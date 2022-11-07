package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.comments.CommentDtoNew;
import ru.practicum.shareit.item.LastBooking;
import ru.practicum.shareit.item.NextBooking;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Builder(toBuilder = true)
@Setter
@Getter
@Valid
public class ItemDto {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Boolean available;
    private UserDto owner;
    private ItemRequestDto itemRequest;
    private LastBooking lastBooking;
    private NextBooking nextBooking;
    private List<CommentDtoNew> comments;
    private Long requestId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return Objects.equals(id, itemDto.id)
                && Objects.equals(name, itemDto.name)
                && Objects.equals(description, itemDto.description)
                && Objects.equals(available, itemDto.available)
                && Objects.equals(owner, itemDto.owner)
                && Objects.equals(itemRequest, itemDto.itemRequest)
                && Objects.equals(lastBooking, itemDto.lastBooking)
                && Objects.equals(nextBooking, itemDto.nextBooking)
                && Objects.equals(comments, itemDto.comments)
                && Objects.equals(requestId, itemDto.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name,
                description, available,
                owner, itemRequest,
                lastBooking, nextBooking,
                comments, requestId);
    }
}
