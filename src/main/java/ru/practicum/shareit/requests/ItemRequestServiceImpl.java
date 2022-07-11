package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.util.UserValidation;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserValidation userValidation;

    @Override
    public ItemRequest add(Long userId, ItemRequest itemRequest) {
        userValidation.isUserRegister(userId);
        return itemRequestRepository.add(userId, itemRequest);
    }

    @Override
    public void delete(Long userId, Long requestId) {
        userValidation.isUserRegister(userId);
        itemRequestRepository.delete(userId, requestId);
    }
}
