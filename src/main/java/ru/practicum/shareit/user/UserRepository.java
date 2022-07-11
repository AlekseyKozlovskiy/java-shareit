package ru.practicum.shareit.user;

import java.util.List;

public interface UserRepository {
    User add(User user);

    User upgrade(Long id, User user);

    User get(Long id);

    void delete(Long id);

    List<User> getAll();
}
