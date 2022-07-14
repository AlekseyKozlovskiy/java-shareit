package ru.practicum.shareit.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.IncorrectHeaderException;
import ru.practicum.shareit.exceptions.IncorrectUserException;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;

@RequiredArgsConstructor
@Component
public class UserValidation {
    private final UserRepository userRepository;


    public Boolean validEmail(UserDto userDto) {
        return userRepository.getAll().stream().noneMatch(user1 -> user1.getEmail().equals(userDto.getEmail()));
    }

    public Boolean isUserRegister(Long userId) {
        if (userId == null) {
            throw new IncorrectHeaderException();
        }
        boolean t = userRepository.getAll().stream().anyMatch(user1 -> user1.getId().equals(userId));
        if (t) {
            return true;
        } else throw new IncorrectUserException(userId);
    }
}
