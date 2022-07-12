package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.util.NumberGenerator;
import ru.practicum.shareit.util.UserValidation;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserValidation userValidation;

    @Override
    public UserDto add(UserDto userDto) {
        if (userValidation.validEmail(userDto)) {
            userDto.setId(NumberGenerator.getUserId());
            User user = UserMapper.toNewUser(userDto);
            return UserMapper.toUserDto(userRepository.add(user));

        } else throw new ValidationException("Данные не верны");
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        if (userValidation.validEmail(userDto)) {
            User user = UserMapper.toNewUser(userDto);
            return UserMapper.toUserDto(userRepository.upgrade(id, user));
        } else throw new ValidationException("Данные не верны");
    }

    @Override
    public UserDto get(Long id) {
        return UserMapper.toUserDto(userRepository.get(id));
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public List<UserDto> getAll() {
        return UserMapper.toUserDtoList(userRepository.getAll());
    }
}
