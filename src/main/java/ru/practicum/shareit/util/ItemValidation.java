package ru.practicum.shareit.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.IncorrectHeaderException;
import ru.practicum.shareit.exceptions.IncorrectItemIdException;
import ru.practicum.shareit.exceptions.IncorrectItemValidException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;

@Component
@RequiredArgsConstructor
public class ItemValidation {
    private final ItemRepository itemRepository;

    public Boolean chek(Long userId, ItemDto itemDto) {

        if (itemDto.getName().isBlank()) {
            throw new IncorrectItemValidException("wrong name");
        }
        if (itemDto.getAvailable() == null) {
            throw new IncorrectItemValidException("wrong available");
        }
        if (itemDto.getDescription() == null) {
            throw new IncorrectItemValidException("wrong description");
        }
        return true;
    }

    public void isItemExist(Long itemId) {
        if (itemId == null) {
            throw new IncorrectHeaderException();
        }
        boolean t = itemRepository.findAll().stream().anyMatch(item -> item.getId().equals(itemId));
        if (!t) {
            throw new IncorrectItemIdException();
        }
    }


    public Boolean isItemAvailable(Long itemId) {
        if (itemRepository.findAll().stream().noneMatch(item -> item.getId().equals(itemId))) {
            throw new IncorrectItemIdException();
        }
        if (!itemRepository.getById(itemId).getAvailable()) {
            throw new IncorrectItemValidException("wrong available");
        }
        return true;
    }

}
