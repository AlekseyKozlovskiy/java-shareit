package ru.practicum.shareit.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.IncorrectItemAvailableException;
import ru.practicum.shareit.exceptions.IncorrectItemDescriptionException;
import ru.practicum.shareit.exceptions.IncorrectItemIdException;
import ru.practicum.shareit.exceptions.IncorrectItemNameException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;

@Component
@RequiredArgsConstructor
public class ItemValidation {
    private final ItemRepository itemRepository;

    public Boolean chek(Long userId, ItemDto itemDto) {

        if (itemDto.getName().isBlank()) {
            throw new IncorrectItemNameException();
        }
        if (itemDto.getAvailable() == null) {
            throw new IncorrectItemAvailableException();
        }
        if (itemDto.getDescription() == null) {
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
