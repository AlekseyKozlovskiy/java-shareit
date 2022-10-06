//package ru.practicum.shareit.user;
//
//import org.springframework.stereotype.Component;
//import ru.practicum.shareit.exceptions.ValidationException;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class UserRepositoryImpl implements UserRepository {
//    Map<Long, User> userMap = new HashMap<>();
//
//    @Override
//    public User add(User user) {
//
//        userMap.put(user.getId(), user);
//        return user;
//    }
//
//    @Override
//    public User upgrade(Long id, User user) {
//        if (userMap.containsKey(id)) {
//            if (user.getName() != null) {
//                userMap.get(id).setName(user.getName());
//            }
//            if (user.getEmail() != null) {
//                userMap.get(id).setEmail(user.getEmail());
//            }
//            return userMap.get(id);
//        } else {
//            throw new ValidationException("данные не верны");
//        }
//
//    }
//
//    @Override
//    public User get(Long id) {
//        return userMap.get(id);
//    }
//
//    @Override
//    public void delete(Long id) {
//        userMap.remove(id);
//    }
//
//    @Override
//    public List<User> getAll() {
//        return new ArrayList<>(userMap.values());
//    }
//}
