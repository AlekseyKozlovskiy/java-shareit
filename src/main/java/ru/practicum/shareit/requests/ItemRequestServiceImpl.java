package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.util.NumberGenerator;
import ru.practicum.shareit.util.UserValidation;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserValidation userValidation;

    @Override
    public ItemRequestDto add(Long userId, ItemRequestDto itemRequestDto) {
        userValidation.isUserRegister(userId);
        itemRequestDto.setId(NumberGenerator.getItemRequestId());
        ItemRequest itemRequest = ItemRequestMapper.toNewItemRequest(itemRequestDto);
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.add(userId, itemRequest));
    }

    @Override
    public void delete(Long userId, Long requestId) {
        userValidation.isUserRegister(userId);
        itemRequestRepository.delete(userId, requestId);
    }
}
