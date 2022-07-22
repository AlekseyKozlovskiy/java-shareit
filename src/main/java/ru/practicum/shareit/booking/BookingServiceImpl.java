package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.util.BookingValidation;
import ru.practicum.shareit.util.ItemValidation;
import ru.practicum.shareit.util.UserValidation;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UserValidation userValidation;
    private final ItemValidation itemValidation;
    private final BookingValidation bookingValidation;

    @Transactional
    @Override
    public BookingDto add(Long userId, BookingDto bookingDto) {
        userValidation.isUserRegister(userId);
        itemValidation.isItemAvailable(bookingDto.getItemId());
        bookingValidation.isBookingValid(bookingDto);
        bookingValidation.tryToBookingSelfItem(bookingDto.getItemId(), userId);
        bookingDto.setBooker(UserMapper.toUserDto(userRepository.getById(userId)));
        bookingDto.setItem(ItemMapper.toItemDto(itemRepository.getById(bookingDto.getItemId())));
        bookingDto.setStatus(BookingStatus.WAITING);
        Booking booking = BookingMapper.toNewBooking(bookingDto);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto upgrade(Long userId, Long bookingId, Boolean bookingStatus) {
        Booking booking = bookingRepository.getById(bookingId);

        if (bookingStatus != null) {
            bookingValidation.isOwnerrrrr(bookingId, userId);
            if (bookingStatus) {
                bookingValidation.isBookingApproved(bookingId);
                booking.setStatus(BookingStatus.APPROVED);
                booking.setApproved(true);
            }
            if (!bookingStatus) {
                booking.setStatus(BookingStatus.REJECTED);
                booking.setApproved(false);
            }
        }
        if (bookingStatus == null) {
            bookingValidation.isOwnerrrrr(bookingId, userId);
            booking.setStatus(BookingStatus.APPROVED);
            booking.setApproved(true);
        }
        bookingRepository.save(booking);

        return BookingMapper.toBookingDto(booking);
    }

    @Override
    @Transactional
    public BookingDto get(Long userId, Long bookingId) {
        bookingValidation.isBookingIdValid(bookingId);
        bookingValidation.isBookingIdAndUserIdMatches(bookingId, userId);
        return BookingMapper.toBookingDto(bookingRepository.getById(bookingId));
    }

    @Override
    public List<BookingDto> getAll(Long userId) {
        List<Booking> list = bookingRepository.getAllByBookerId(userId);
        list.sort((b, b1) -> (int) (b1.getId() - b.getId()));

        return BookingMapper.toBookingDtoList(list);
    }

    @Override
    public List<BookingDto> getAllOfOwner(Long userId) {
        List<Booking> list = bookingRepository.getAllOfOwner(userId);
        list.sort((b, b1) -> (int) (b1.getId() - b.getId()));
        return BookingMapper.toBookingDtoList(list);
    }
}
