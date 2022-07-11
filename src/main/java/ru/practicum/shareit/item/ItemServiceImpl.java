package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.util.ItemValidation;
import ru.practicum.shareit.util.UserValidation;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemValidation itemValidation;
    private final UserValidation userValidation;

    @Override
    public Item addNewItem(Long userId, Item item) {
        userValidation.isUserRegister(userId);
        itemValidation.chek(userId, item);
        return itemRepository.add(userId, item);
    }

    @Override
    public Item upgradeItem(Long userId, Item item, Long itemId) {
        userValidation.isUserRegister(userId);
        return itemRepository.upgrade(userId, item, itemId);

    }

    @Override
    public Item get(Long userId, Long itemId) {
        if (userValidation.isUserRegister(userId)) {
            return itemRepository.get(itemId);
        } else throw new IncorrectUserException(userId);
    }

    @Override
    public List<Item> getAll(Long userId) {
        if (userValidation.isUserRegister(userId)) {
            return itemRepository.getAll(userId);
        } else throw new IncorrectOwnerException();
    }

    @Override
    public List<Item> search(String item) {
        return itemRepository.search(item);
    }
}
