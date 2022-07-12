package ru.practicum.shareit.user;


import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto add(UserDto userDto);

    UserDto update(Long id, UserDto userDto);

    UserDto get(Long id);

    void delete(Long id);

    List<UserDto> getAll();
}
