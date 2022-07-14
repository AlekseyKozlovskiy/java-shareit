package ru.practicum.shareit.booking;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Component
public class Booking {
    private Long id;

    @NotBlank
    private LocalDate start;

    @NotBlank
    private LocalDate end;

    private Item item;

    private User booker;

    private BookingStatus bookingStatus;

}
