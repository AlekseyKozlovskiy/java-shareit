package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto addNewItem(Long userId, ItemDto itemDto);

    ItemDto upgradeItem(Long userId, ItemDto itemDto, Long itemId);

    ItemDto get(Long userId, Long itemId);

    List<ItemDto> getAll(Long userId);

    List<ItemDto> search(String item);
}
