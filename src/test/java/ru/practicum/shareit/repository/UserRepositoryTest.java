//package ru.practicum.shareit.repository;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import ru.practicum.shareit.ShareItTests;
//import ru.practicum.shareit.user.User;
//import ru.practicum.shareit.user.UserRepository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class UserRepositoryTest extends ShareItTests {
//    private final UserRepository userRepository;
//
//    User user = User.builder().id(100L).name("name").email("email").build();
//    User user1 = User.builder().id(101L).name("name2").email("email2").build();
//
//    @Autowired
//    public UserRepositoryTest(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Test
//    public void addTest() {
//        assertEquals(0, userRepository.getAll().size());
//        userRepository.adddsfsdfsdf(user);
//        assertEquals(1, userRepository.getAll().size());
//    }
//
//    @Test
//    public void update() {
//        userRepository.adddsfsdfsdf(user);
//        User user3 = User.builder().name("newName").email("new Email").build();
//        userRepository.upgrade(user.getId(), user3);
//        assertEquals("new Email", userRepository.getGetGEt(user.getId()).getEmail());
//    }
//
//    @Test
//    public void get() {
//        userRepository.adddsfsdfsdf(user);
//        userRepository.adddsfsdfsdf(user1);
//        assertEquals("name", userRepository.getGetGEt(user.getId()).getName());
//        assertEquals(2, userRepository.getAll().size());
//    }
//
//    @Test
//    public void delete() {
//        userRepository.adddsfsdfsdf(user);
//        userRepository.adddsfsdfsdf(user1);
//        userRepository.delete(user.getId());
//        assertEquals(1, userRepository.getAll().size());
//    }
//
//
//}
