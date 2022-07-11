package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item addNewItem(Long userId, Item item);

    Item upgradeItem(Long userId, Item item, Long itemId);

    Item get(Long userId, Long itemId);

    List<Item> getAll(Long userId);

    List<Item> search(String item);
}
