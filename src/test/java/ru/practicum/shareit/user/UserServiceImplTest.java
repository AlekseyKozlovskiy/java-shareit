package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.util.UserDtoCreater;

import java.util.List;

//@WebMvcTest(UserServiceImpl.class)
class UserServiceImplTest extends ShareItTests {
    //    @MockBean
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @MockBean
    UserRepository userRepository;
//    @MockBean
//    private UserValidation userValidation;
//    private User user;

//    @BeforeEach
//    void beforeEach() {
//        userRepository = Mockito.mock(UserRepository.class);
//        userService = new UserServiceImpl(userRepository , userValidation);
//        user = new User(1L, "user 1", "user1@email");
//    }

    @Test
    void add() {
        UserDto userDto = UserDtoCreater.getUserDto();
        User user = userMapper.toNewUser(userDto);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserDto add = userService.add(userDto);
        Assertions.assertTrue(add.getId() == 1L);
    }

    @Test
    void upgrade() {
    }

    @Test
    void get() {
        UserDto userDto = UserDtoCreater.getUserDto();
        User user = userMapper.toNewUser(userDto);
        Mockito.when(userRepository.getById(1L)).thenReturn(user);
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        UserDto userDto1 = userService.get(1L);
        Assertions.assertEquals(userDto, userDto1);
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
//        final PageImpl<User> userPage = new PageImpl<>(Collections.singletonList(user));
//        when(userRepository.findAll(PageRequest.ofSize(10)))
//                .thenReturn(userPage);
//
//        final List<UserDto> userDtos = userService.getAll(PageRequest.ofSize(10));
//
//        assertNotNull(userDtos);
//        assertEquals(1, userDtos.size());
//        assertEquals(user, userDtos.get(0));
    }
}