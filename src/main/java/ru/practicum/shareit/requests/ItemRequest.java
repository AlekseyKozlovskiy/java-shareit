package ru.practicum.shareit.requests;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.User;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Valid
@Builder
public class ItemRequest {
    private Long id;
    @NotBlank
    private String description;
    @NotBlank
    private User requestor;
    @NotBlank
    private LocalDate created;
}
