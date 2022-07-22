package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.util.NumberGenerator;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ItemRequestRepositoryImpl implements ItemRequestRepository {
    Map<Long, ItemRequest> itemRequestMap = new HashMap<>();

    @Override
    public ItemRequest add(Long userId, ItemRequest itemRequest) {
        itemRequest.setId(NumberGenerator.getItemRequestId());
        itemRequestMap.put(itemRequest.getId(), itemRequest);
        return itemRequest;
    }

    @Override
    public void delete(Long userId, Long requestId) {
    }
}
