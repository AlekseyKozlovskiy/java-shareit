package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.ItemRequest;

import java.util.List;
import java.util.Map;

public interface ItemRepository {
    Item add(Long userId, Item item);
    Item get(Long itemId);
    void delete();
    List<Item> getAll(Long userId);
    List<Item> search(String item);
    Item upgrade(Long userId, Item item, Long itemId);
    Map<Long, Item> getItemMap();
}
