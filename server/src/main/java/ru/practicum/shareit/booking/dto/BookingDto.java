package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder(toBuilder = true)
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDto that = (BookingDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(itemId, that.itemId)
                && Objects.equals(start, that.start)
                && Objects.equals(end, that.end)
                && Objects.equals(booker, that.booker)
                && status == that.status
                && Objects.equals(approved, that.approved)
                && Objects.equals(item, that.item)
                && Objects.equals(canceled, that.canceled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId, start, end, booker, status, approved, item, canceled);
    }
}
