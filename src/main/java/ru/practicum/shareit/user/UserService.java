package ru.practicum.shareit.user;


import java.util.List;

public interface UserService {
    User add(User user);
    User update(Long id, User user);
    User get(Long id);
    void delete(Long id);
    List<User> getAll();
}
