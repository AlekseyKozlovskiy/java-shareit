package ru.practicum.shareit.booking;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> getAllByBookerIdOrderByIdDesc(Long userId, Pageable pageable);

    List<Booking> getAllByBookerIdAndStartIsAfterOrderByIdDesc(Long userId, LocalDateTime end);

    List<Booking> getAllByBookerIdAndStatusOrderByIdDesc(Long userId, BookingStatus status);

    List<Booking> getAllByBookerIdAndEndIsBeforeOrderByIdDesc(Long userId, LocalDateTime end);

    List<Booking> getAllByBookerIdAndEndIsAfterAndStartIsBefore(Long userId, LocalDateTime end, LocalDateTime start);

    @Query(value = "select * from shareit.bookings as b inner join shareit.items as i on b.item_id = i.id " +
            "where i.owner_id = ?1 ORDER BY b.id DESC", nativeQuery = true)
    List<Booking> getAllOfOwner(Long userId, Pageable pageable);

    @Query(value = "select * from shareit.bookings as b inner join shareit.items as i on b.item_id = i.id " +
            "where i.owner_id = ?1 and b.status like ?2 ORDER BY b.id DESC", nativeQuery = true)
    List<Booking> getAllOfOwnerAndStatus(Long userId, String status, PageRequest pageRequest);

    List<Booking> getAllByItem_OwnerIdAndEndIsBefore(Long userId, LocalDateTime localDateTime);

    @Query(value = "select * from shareit.bookings as b inner join shareit.items as i on b.item_id = i.id " +
            "where i.owner_id = ?1 and b.end_date_time >= ?2 and b.start_date_time <= ?2 ORDER BY b.id DESC", nativeQuery = true)
    List<Booking> getAllByOwnerCurrent(Long userId, LocalDateTime localDateTime);

    List<Booking> findByItemId(Long itemId);

    List<Booking> findAllByItemIdAndBookerIdAndEndIsBefore(Long itemId, Long bookerId, LocalDateTime localDateTime);
}
