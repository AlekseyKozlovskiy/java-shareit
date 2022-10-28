package ru.practicum.shareit.util;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.requests.ItemRequestRepository;
import ru.practicum.shareit.exceptions.IncorrectRequest;
import ru.practicum.shareit.exceptions.ValidationException;

@Component
public class ItemRequestValidation {
    ItemRequestRepository itemRequestRepository;

    public ItemRequestValidation(ItemRequestRepository itemRequestRepository) {
        this.itemRequestRepository = itemRequestRepository;
    }

    public void isDescriptionValid(String s) {

        if (s == null) {
            throw new ValidationException("Description is empty");
        }
    }

    public void isItemRequestExists(Long itemRequestId) {
        if (!itemRequestRepository.findAll().stream().anyMatch(i -> i.getId().equals(itemRequestId))) {
            throw new IncorrectRequest("Wrong item request id");
        }
    }
}
