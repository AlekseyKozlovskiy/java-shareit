package ru.practicum.shareit.util;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.comments.CommentDtoNew;
import ru.practicum.shareit.exceptions.IncorrectRequest;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.LastBooking;
import ru.practicum.shareit.item.NextBooking;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class BookingValidation {
    BookingRepository bookingRepository;
    ItemRepository itemRepository;

    public BookingValidation(BookingRepository bookingRepository, ItemRepository itemRepository) {
        this.bookingRepository = bookingRepository;
        this.itemRepository = itemRepository;
    }

    public Boolean isBookingValid(BookingDto bookingDto) {
        if (bookingDto.getEnd().isBefore(LocalDateTime.now())
                || bookingDto.getStart().isBefore(LocalDateTime.now())
                || bookingDto.getEnd().isBefore(bookingDto.getStart())) {
            throw new ValidationException("wrong time");
        }
        return true;
    }

    public Boolean isBookingIdValid(Long bookingId) {
        if (!bookingRepository.findAll().stream().anyMatch(booking -> booking.getId().equals(bookingId))) {
            throw new IncorrectRequest("Неверный ID Booking");
        }
        return true;
    }

    public Boolean isBookingIdAndUserIdMatches(Long bookingId, Long userId) {
        System.out.println(bookingRepository.getById(bookingId).getItem().getOwner().getId());
        if (!bookingRepository.getById(bookingId).getItem().getOwner().getId().equals(userId)
                && !bookingRepository.getById(bookingId).getBooker().getId().equals(userId)) {
            throw new IncorrectRequest("Только владелец может поменять статус");
        }
        return true;
    }

    public Boolean isBookingApproved(Long bookingId) {
        if (bookingRepository.getById(bookingId).getApproved()) {
            throw new ValidationException("Вещь уже подтверждена для бронирования");
        }
        return true;
    }

    public Boolean isOwner(Long bookingId, Long userId) {
        if (!bookingRepository.getById(bookingId).getItem().getOwner().getId().equals(userId)) {
            throw new IncorrectRequest("Только владелец может поменять статус");
        }
        return true;
    }


    public Boolean tryToBookingSelfItem(Long itemId, Long userId) {
        if (itemRepository.getById(itemId).getOwner().getId().equals(userId)) {
            throw new IncorrectRequest("ОШИБКА");
        }
        return true;
    }

    public LastBooking lastBooking(Long itemId) {
        Booking booking1 = bookingRepository.getFirstByItemIdOrderByStartAsc(itemId);
        LastBooking lastBooking = new LastBooking();
        if (booking1 == null) {
            lastBooking.setId(null);
            lastBooking.setBookerId(null);
            return lastBooking;
        }
        lastBooking.setId(booking1.getId());
        lastBooking.setBookerId(booking1.getBooker().getId());
        return lastBooking;
    }

    public NextBooking nextBooking(Long itemId) {
        Booking booking1 = bookingRepository.getFirstByItemIdOrderByEndDesc(itemId);
        NextBooking nextBooking = new NextBooking();

        if (booking1 == null) {
            nextBooking.setId(null);
            nextBooking.setBookerId(null);
            return nextBooking;
        }
        nextBooking.setId(booking1.getId());
        nextBooking.setBookerId(booking1.getBooker().getId());
        return nextBooking;
    }

    public Boolean test(Long itemId, Long userId) {
        return itemRepository.getById(itemId).getOwner().getId().equals(userId);
    }

    public void isCommentAvailable(Long itemId, Long userId, CommentDtoNew commentDtoNew) {
        List<Booking> bookingList = bookingRepository.findAllByItemIdAndBookerIdAndEndIsBefore(itemId, userId, LocalDateTime.now());
        if (commentDtoNew.getText().isBlank()
                || !bookingRepository.getById(itemId).getBooker().getId().equals(userId)
                || !bookingRepository.getById(itemId).getItem().getId().equals(itemId)) {
            throw new ValidationException("Вещь не была в аренде");
        }
        for (Booking booking : bookingList) {
            if (booking.getStatus().equals(BookingStatus.REJECTED)) {
                throw new ValidationException("Вещь не была в аренде");
            }
        }
    }

    public boolean isStateCorrect(String state) {
        boolean b = Arrays.stream(State.values()).anyMatch(s -> s.toString().equals(state));
        if (!b) {
            throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
        return b;


    }

}
