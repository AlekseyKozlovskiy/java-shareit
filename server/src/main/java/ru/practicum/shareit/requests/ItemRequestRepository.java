package ru.practicum.shareit.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> getAllByRequester_Id(Long userId);

    @Query(value = "select * from SHAREIT.ITEM_REQUESTS as ir " +
            "inner join SHAREIT.ITEMS I on ir.ID = I.REQUEST_ID " +
            "where I.ID =?1 ORDER BY ir.ID DESC ", nativeQuery = true)
    List<ItemRequest> ggg(Long userId);


}
