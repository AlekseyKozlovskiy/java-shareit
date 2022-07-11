package ru.practicum.shareit.user;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * // TODO .
 */
@Setter
@Getter
@Builder
@Valid
public class User {
    Long id;

    @NotBlank
    String name;

    @Email
    @NotBlank
    String email;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
