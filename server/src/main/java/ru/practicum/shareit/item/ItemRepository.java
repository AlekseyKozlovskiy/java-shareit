package ru.practicum.shareit.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "select * from SHAREIT.ITEMS WHERE OWNER_ID =?1", nativeQuery = true)
    List<Item> ggg(Long userId);

    Page<Item> searchAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableIsTrue(
            String name, String description, Pageable pageable);

    List<Item> findByOwnerIdOrderById(Long userId);
}
