package ru.practicum.shareit.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> getAllByRequester_Id(Long userId);
}
