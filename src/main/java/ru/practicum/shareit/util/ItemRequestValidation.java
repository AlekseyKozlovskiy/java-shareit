package ru.practicum.shareit.util;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.IncorrectItemRequestDescription;
import ru.practicum.shareit.exceptions.IncorrectItemRequestIdException;
import ru.practicum.shareit.requests.ItemRequestRepository;

@Component
public class ItemRequestValidation {
    ItemRequestRepository itemRequestRepository;

    public ItemRequestValidation(ItemRequestRepository itemRequestRepository) {
        this.itemRequestRepository = itemRequestRepository;
    }

    public void isDescriptionValid(String s) {

        if (s == null) {
            throw new IncorrectItemRequestDescription();
        }
    }

    public void isItemRequestExists(Long itemRequestId) {
        if (!itemRequestRepository.findAll().stream().anyMatch(i -> i.getId().equals(itemRequestId))) {
            throw new IncorrectItemRequestIdException();
        }
    }


}
