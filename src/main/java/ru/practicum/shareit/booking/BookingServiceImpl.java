package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.util.BookingValidation;
import ru.practicum.shareit.util.ItemValidation;
import ru.practicum.shareit.util.UserValidation;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
    public BookingDtoCreate add(Long userId, BookingDto bookingDto) {
        userValidation.isUserRegister(userId);
        itemValidation.isItemAvailable(bookingDto.getItemId());
        bookingValidation.isBookingValid(bookingDto);
        bookingValidation.tryToBookingSelfItem(bookingDto.getItemId(), userId);
        bookingDto.setBooker(UserMapper.toUserDto(userRepository.getById(userId)));
        bookingDto.setItem(ItemMapper.toItemDto(itemRepository.getById(bookingDto.getItemId())));
        bookingDto.setStatus(BookingStatus.WAITING);
        Booking booking = BookingMapper.toNewBooking(bookingDto);
        return BookingMapper.toBookingDtoCreate(bookingRepository.save(booking));
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
            bookingValidation.isOwnerr(bookingId, userId);
            booking.setStatus(BookingStatus.APPROVED);
            booking.setApproved(true);
        }
        bookingRepository.save(booking);

        return BookingMapper.toBookingDto(booking);
    }

    @Override
    @Transactional
    public BookingDto get(Long userId, Long bookingId) {
        userValidation.isUserRegister(userId);
        bookingValidation.isBookingIdValid(bookingId);
        bookingValidation.isBookingIdAndUserIdMatches(bookingId, userId);
        return BookingMapper.toBookingDto(bookingRepository.getById(bookingId));
    }

    @Override
    public List<BookingDto> getAll(Long userId, String state) {
        userValidation.isUserRegister(userId);
        bookingValidation.isStateCorrect(state);
        if (state.equals(State.FUTURE.toString())) {
            List<Booking> list = bookingRepository
                    .getAllByBookerIdAndStartIsAfterOrderByIdDesc(userId, LocalDateTime.now());

            return BookingMapper.toBookingDtoList(list);
        }
        if (state.equals(State.WAITING.toString())) {
            List<Booking> list = bookingRepository.getAllByBookerIdAndStatusOrderByIdDesc(userId, BookingStatus.WAITING);
            return BookingMapper.toBookingDtoList(list);
        }
        if (state.equals(State.REJECTED.toString())) {
            List<Booking> list = bookingRepository.getAllByBookerIdAndStatusOrderByIdDesc(userId, BookingStatus.REJECTED);
            return BookingMapper.toBookingDtoList(list);
        }
        if (state.equals(State.CURRENT.toString())) {
            List<Booking> list = bookingRepository
                    .getAllByBookerIdAndEndIsAfterAndStartIsBefore(userId, LocalDateTime.now(), LocalDateTime.now());
            return BookingMapper.toBookingDtoList(list);
        }


        if (state.equals(State.PAST.toString())) {
            List<Booking> list = bookingRepository
                    .getAllByBookerIdAndEndIsBeforeOrderByIdDesc(userId, LocalDateTime.now());
            return BookingMapper.toBookingDtoList(list);
        }
        List<Booking> list = bookingRepository.getAllByBookerId(userId);
        list.sort((b, b1) -> (int) (b1.getId() - b.getId()));

        return BookingMapper.toBookingDtoList(list);
    }

    @Override
    public List<BookingDto> getAllOfOwner(Long userId, String state) {
        userValidation.isUserRegister(userId);
        bookingValidation.isStateCorrect(state);
        if (state.equals(State.WAITING.toString())) {
            List<Booking> list = bookingRepository.getAllOfOwnerAndStatus(userId, BookingStatus.WAITING.toString());
            return BookingMapper.toBookingDtoList(list);
        }
        if (state.equals(State.REJECTED.toString())) {
            List<Booking> list = bookingRepository.getAllOfOwnerAndStatus(userId, BookingStatus.REJECTED.toString());
            return BookingMapper.toBookingDtoList(list);
        }
        if (state.equals(State.CURRENT.toString())) {
            List<Booking> list = bookingRepository.getAllByOwnerCurrent(userId, LocalDateTime.now());
            return BookingMapper.toBookingDtoList(list);
        }
        if (state.equals(State.PAST.toString())) {
            List<Booking> list = bookingRepository.getAllByItem_OwnerIdAndEndIsBefore(userId, LocalDateTime.now());
            return BookingMapper.toBookingDtoList(list);
        }

        List<Booking> list = bookingRepository.getAllOfOwner(userId);
        return BookingMapper.toBookingDtoList(list);
    }
}
