package ru.practicum.shareit.item.util;

import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

public class ItemDtoCreater {

    public static ItemDto getItemDto() {
        User user = new User(1L, "user 1", "user1@email");
        Item item = new Item(1L, "item name", "description", true, user, null);

        return ItemMapper.toItemDto(item);
    }
}
