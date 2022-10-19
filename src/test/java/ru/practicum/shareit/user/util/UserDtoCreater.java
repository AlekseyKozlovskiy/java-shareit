package ru.practicum.shareit.user.util;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;

public class UserDtoCreater {

    public static UserDto getUserDto() {
        User user = new User(1L, "user 1", "user1@email");
        return UserMapper.toUserDto(user);
    }
}
