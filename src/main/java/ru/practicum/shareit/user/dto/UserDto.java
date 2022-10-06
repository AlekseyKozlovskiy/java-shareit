package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Valid
@Setter
@Getter
public class UserDto {
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

}

