package ru.practicum.shareit.user.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Valid
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class UserDto {
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

}

