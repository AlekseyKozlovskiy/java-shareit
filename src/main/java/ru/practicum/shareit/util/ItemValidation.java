package ru.practicum.shareit.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.*;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;

@Component
@RequiredArgsConstructor
public class ItemValidation {
    private final ItemRepository itemRepository;

    public Boolean chek(Long userId, Item item) {

        if (item.getName().isBlank()) {
            throw new IncorrectItemNameException();
        }
        if (item.getAvailable() == null) {
            throw new IncorrectItemAvailableException();
        }
        if (item.getDescription() == null) {
            throw new IncorrectItemDescriptionException();
        }
        return true;
    }

    public Boolean chekItemToBooking(Long itemId) {
        if (itemRepository.getItemMap().containsKey(itemId)
                && itemRepository.get(itemId).getAvailable()) {
            return true;
        } else {
            throw new IncorrectItemIdException();
        }
    }

}
