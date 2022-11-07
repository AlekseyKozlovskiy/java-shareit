package ru.practicum.shareit.booking;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exceptions.ValidationException;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class BookingValidation {
    public void validate(BookingDto dto) {
        if (dto.getEnd().isBefore(dto.getStart()) || dto.getStart().isBefore(LocalDateTime.now()) ||
                dto.getEnd().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Ошибка времени");
        }
    }

    public boolean isStateCorrect(String state) {
        boolean b = Arrays.stream(State.values()).anyMatch(s -> s.toString().equals(state));
        if (!b) {
            throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
        return b;
    }
}
