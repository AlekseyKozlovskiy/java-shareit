package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.util.UserValidation;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserValidation userValidation;

    @Override
    public UserDto add(UserDto userDto) {

        return UserMapper.toUserDto(userRepository.save(UserMapper.toNewUser(userDto)));
    }

    @Override
    public UserDto upgrade(Long id, UserDto userDto) {
        User user = UserMapper.toNewUser(userDto);
        user.setId(id);
        if (userDto.getEmail() == null) {
            user.setEmail(userRepository.getById(id).getEmail());
        }
        if (userDto.getName() == null) {
            user.setName(userRepository.getById(id).getName());
        }
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto get(Long id) {
        userValidation.isUserRegister(id);
        return UserMapper.toUserDto(userRepository.getById(id));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getAll() {
        return UserMapper.toUserDtoList(userRepository.findAll());
    }
}
