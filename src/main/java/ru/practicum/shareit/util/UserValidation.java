package ru.practicum.shareit.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.IncorrectRequest;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.UserRepository;

@RequiredArgsConstructor
@Component
public class UserValidation {
    private final UserRepository userRepository;


    public Boolean isUserRegister(Long userId) {
        if (userId == null) {
            throw new ValidationException("Ошибка в заголовке");
        }
        boolean t = userRepository.findAll().stream().anyMatch(user1 -> user1.getId().equals(userId));
        if (t) {
            return true;
        } else throw new IncorrectRequest("Пользователя с таким ID не существует");
    }
}
