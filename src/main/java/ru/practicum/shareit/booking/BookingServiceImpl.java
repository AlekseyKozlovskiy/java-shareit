package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.util.ItemValidation;
import ru.practicum.shareit.util.UserValidation;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserValidation userValidation;
    private final ItemValidation itemValidation;

    @Override
    public Booking add(Long userId, Long itemId, Booking booking) {
        userValidation.isUserRegister(userId);
        itemValidation.chekItemToBooking(itemId);
        return bookingRepository.add(userId, itemId, booking);
    }
}
