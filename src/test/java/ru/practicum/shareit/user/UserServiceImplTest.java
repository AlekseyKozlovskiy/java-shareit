package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.user.dto.UserDto;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional
class UserServiceImplTest extends ShareItTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;
    static User user = new User(1L, "Simple User", "user@mail.ru");
    static User user2 = new User(2L, "Another User", "test@mail.ru");

    @Autowired
    public UserServiceImplTest(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        userRepository.save(user);
        userRepository.save(user2);
        this.userService = userService;
    }

    @Test
    void add() {
        UserDto user1 = UserDto.builder().id(1L).name("Simple User").email("user@mail.ru").build();
        assertEquals(userRepository.findById(user1.getId()).orElse(null), user);
    }

    @Test
    void upgrade() {
        UserDto userDto = UserMapper.toUserDto((user));
        userDto.setName(null);
        userDto.setEmail(null);
        userService.upgrade(1L, userDto);
        assertEquals(userRepository.findById(1L).orElse(null), UserMapper.toNewUser(userDto));
        userDto.setName("New Name");
        userDto.setEmail("em@em.ru");
        userService.upgrade(1L, userDto);
        assertEquals(userRepository.findById(1L).orElse(null), UserMapper.toNewUser(userDto));
    }

    @Test
    void get() {
        assertEquals(UserMapper.toUserDto(user), userService.get(1L));
    }

    @Test
    void deleteUser() {
        userService.delete(user.getId());
        assertNull(userRepository.findById(user.getId()).orElse(null));
    }

    @Test
    void getAll() {
        List<UserDto> all = userService.getAll(PageRequest.ofSize(10));
        assertEquals(List.of(UserMapper.toUserDto(user),
                UserMapper.toUserDto(user2)), all);
    }
}