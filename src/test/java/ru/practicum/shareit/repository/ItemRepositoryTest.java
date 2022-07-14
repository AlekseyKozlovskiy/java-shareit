package ru.practicum.shareit.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemRepositoryTest extends ShareItTests {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemRepositoryTest(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    User user = User.builder().id(1L).name("name").email("email").build();
    Item item = Item.builder().name("itemName").description("description").available(true).owner(user).build();

    @Test
    public void addTest() {
        userRepository.add(user);
        assertEquals(0, itemRepository.getAll(userRepository.get(user.getId()).getId()).size());

        itemRepository.add(user.getId(), item);
        assertEquals(1, itemRepository.getAll(userRepository.get(user.getId()).getId()).size());
    }


    @Test
    public void getTest() {
        userRepository.add(user);
        itemRepository.add(user.getId(), item);
        assertEquals("itemName", itemRepository.get(item.getId()).getName());
    }

}
