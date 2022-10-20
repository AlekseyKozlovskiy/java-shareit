package ru.practicum.shareit.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class LastBooking {
    private Long id;
    private Long bookerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastBooking that = (LastBooking) o;
        return Objects.equals(id, that.id) && Objects.equals(bookerId, that.bookerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookerId);
    }
}
