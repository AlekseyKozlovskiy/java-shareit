package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.util.UserValidation;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserValidation userValidation;

    @Override
    public User add(User user) {
        if (userValidation.validEmail(user)) {
            return userRepository.add(user);

        }
        else throw new ValidationException("Данные не верны");
    }

    @Override
    public User update(Long id, User user) {
        if (userValidation.validEmail(user)) {
            return userRepository.upgrade(id, user);
        }
        else throw new ValidationException("Данные не верны");
    }

    @Override
    public User get(Long id) {
        return userRepository.get(id);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }
}
