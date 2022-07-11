package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.IncorrectOwnerException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.util.NumberGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final UserRepository userRepository;
    Map<Long, Item> itemMap = new HashMap<>();


    @Override
    public Item add(Long userId, Item item) {
        item.setId(NumberGenerator.getItemId());
        item.setOwner(userRepository.get(userId));
        itemMap.put(item.getId(), item);
        return item;
    }

    @Override
    public Item get(Long itemId) {
        return itemMap.get(itemId);
    }

    @Override
    public List<Item> getAll(Long userId) {
        return new ArrayList<>(itemMap.values()).stream().filter(i -> i.getOwner().getId() == userId).collect(Collectors.toList());
    }

    @Override
    public List<Item> search(String item) {
        List<Item> items = new ArrayList<>();
        if (item.isBlank()) {
            return items;
        }
        items = itemMap.values().stream()
                .filter(i -> i.getAvailable().equals(true))
                .filter(i -> i.getName().toLowerCase().contains(item.toLowerCase())
                        || i.getDescription().toLowerCase().contains(item.toLowerCase()))
                .collect(Collectors.toList());
        return items;
    }

    @Override
    public Item upgrade(Long userId, Item item, Long itemId) {
        if (itemMap.get(itemId).getOwner().getId() == userId) {
            upgrade(item, itemId);
            return itemMap.get(itemId);
        } else throw new IncorrectOwnerException();
    }

    @Override
    public void delete() {

    }

    void upgrade(Item item, Long itemId) {
        Item item1 = itemMap.get(itemId);
        if (item.getName() != null) {
            item1.setName(item.getName());
        }
        if (item.getDescription() != null) {
            item1.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            item1.setAvailable(item.getAvailable());
        }
    }

    @Override
    public Map<Long, Item> getItemMap() {
        return itemMap;
    }
}
