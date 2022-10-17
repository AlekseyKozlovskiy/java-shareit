package ru.practicum.shareit.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Valid
@Entity
@Builder
@Table(name = "users", schema = "shareit")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    String name;

    @Email
    @NotBlank
    @Column(name = "email")
    String email;
}
