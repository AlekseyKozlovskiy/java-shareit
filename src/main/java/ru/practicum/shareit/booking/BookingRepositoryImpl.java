//package ru.practicum.shareit.booking;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import ru.practicum.shareit.item.ItemRepository;
//import ru.practicum.shareit.user.UserRepository;
//import ru.practicum.shareit.util.NumberGenerator;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class BookingRepositoryImpl implements BookingRepository {
//    private final UserRepository userRepository;
//    private final ItemRepository itemRepository;
//    Map<Long, Booking> bookingMap = new HashMap<>();
//
//    @Override
//    public Booking add(Long userId, Long itemId, Booking booking) {
////        booking.setId(NumberGenerator.getBookingId());
////        booking.setItem(itemRepository.get(itemId));
//////        booking.setBooker(userRepository.getGetGEt(userId));
////        booking.setBookingStatus(BookingStatus.WAITING);
////        bookingMap.put(booking.getId(), booking);
//        return booking;
//    }
//}
