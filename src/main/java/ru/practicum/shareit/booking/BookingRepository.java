package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> getAllByBookerId(Long userId);

    List<Booking> getAllByBookerIdAndStartIsAfterOrderByIdDesc(Long userId, LocalDateTime end);
    List<Booking> getAllByBookerIdAndStatusOrderByIdDesc(Long userId, BookingStatus status);

    List<Booking> getAllByBookerIdAndEndIsBeforeOrderByIdDesc(Long userId, LocalDateTime end);

    @Query(value = "select * from shareit.bookings as b inner join shareit.items as i on b.item_id = i.id " +
            "where i.owner_id = ?1 ORDER BY b.id DESC", nativeQuery = true)
    List<Booking> getAllOfOwner(Long userId);

    List<Booking> findByItemId(Long itemId);

    List<Booking> findAllByItemIdAndBookerIdAndEndIsBefore(Long itemId, Long bookerId, LocalDateTime localDateTime);
}
