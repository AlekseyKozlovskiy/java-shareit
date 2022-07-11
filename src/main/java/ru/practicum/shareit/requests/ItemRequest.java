package ru.practicum.shareit.requests;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * // TODO .
 */
@Data
@Component
public class ItemRequest {
    private Long id;
    @NotBlank
    private String description;
    @NotBlank
    private User requestor;
    @NotBlank
    private LocalDate created;
}
