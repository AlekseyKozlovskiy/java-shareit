package ru.practicum.shareit.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.exceptions.IncorrectRequest;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;

@Component
@RequiredArgsConstructor
public class ItemValidation {
    private final ItemRepository itemRepository;

    public Boolean chek(Long userId, ItemDto itemDto) {

        if (itemDto.getName().isBlank()) {
            throw new ValidationException("wrong name");
        }
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("wrong available");
        }
        if (itemDto.getDescription() == null) {
            throw new ValidationException("wrong description");
        }
        return true;
    }

    public void isItemExist(Long itemId) {
        if (itemId == null) {
            throw new ValidationException("Ошибка в заголовке");
        }
        boolean t = itemRepository.findAll().stream().anyMatch(item -> item.getId().equals(itemId));
        if (!t) {
            throw new IncorrectRequest("Нет вещи с таким ID");
        }
    }


    public Boolean isItemAvailable(Long itemId) {
        if (itemRepository.findAll().stream().noneMatch(item -> item.getId().equals(itemId))) {
            throw new IncorrectRequest("Нет вещи с таким ID");
        }
        if (!itemRepository.getById(itemId).getAvailable()) {
            throw new ValidationException("wrong available");
        }
        return true;
    }

}
