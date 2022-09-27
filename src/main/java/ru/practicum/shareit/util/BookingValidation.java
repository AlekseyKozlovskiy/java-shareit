package ru.practicum.shareit.util;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.comments.CommentDtoNew;
import ru.practicum.shareit.exceptions.*;
import ru.practicum.shareit.item.ItemRepository;
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
            throw new IncorrectBookingTimeException();
        }
        return true;
    }

    public Boolean isBookingIdValid(Long bookingId) {
        if (!bookingRepository.findAll().stream().anyMatch(booking -> booking.getId().equals(bookingId))) {
            throw new IncorrectBookingIdException();
        }
        return true;
    }

    public Boolean isBookingIdAndUserIdMatches(Long bookingId, Long userId) {
        System.out.println(bookingRepository.getById(bookingId).getItem().getOwner().getId());
        if (!bookingRepository.getById(bookingId).getItem().getOwner().getId().equals(userId)
                && !bookingRepository.getById(bookingId).getBooker().getId().equals(userId)) {
            throw new IncorrectChangeStatusBookerException();
        }
        return true;
    }

    public Boolean isBookingApproved(Long bookingId) {
        System.out.println(bookingRepository.getById(bookingId).getApproved());
        if (bookingRepository.getById(bookingId).getApproved()) {
            throw new IncorrectItemApprovedException();
        }
        return true;
    }

    public Boolean isOwnerrrrr(Long bookingId, Long userId) {
        if (!bookingRepository.getById(bookingId).getItem().getOwner().getId().equals(userId)) {
            throw new IncorrectOwnerBookingException();
        }
        return true;
    }

    public Boolean isOwnerr(Long bookingId, Long userId) {
        if (!bookingRepository.getById(bookingId).getItem().getOwner().getId().equals(userId)) {
            throw new IncorrectOwnerBookingExceptions();
        }
        return true;
    }

    public Boolean tryToBookingSelfItem(Long itemId, Long userId) {
        if (itemRepository.getById(itemId).getOwner().getId().equals(userId)) {
            throw new TryingToBookingSelfItemException();
        }
        return true;
    }

    public LastBooking lastBooking(Long itemId) {
        List<Booking> byItemId = bookingRepository.findByItemId(itemId);
        LastBooking lastBooking = new LastBooking();
        Booking booking = null;
        try {
            booking = byItemId.get(0);
        } catch (IndexOutOfBoundsException e) {
            lastBooking.setId(null);
            lastBooking.setBookerId(null);
            return lastBooking;
        }
        lastBooking.setId(booking.getId());
        lastBooking.setBookerId(booking.getBooker().getId());
        return lastBooking;
    }

    public NextBooking nextBooking(Long itemId) {
        List<Booking> byItemId = bookingRepository.findByItemId(itemId);

        NextBooking nextBooking = new NextBooking();
        Booking booking = null;
        try {
            booking = byItemId.get(1);
        } catch (IndexOutOfBoundsException e) {
            nextBooking.setId(null);
            nextBooking.setBookerId(null);
            return nextBooking;
        }
        nextBooking.setId(booking.getId());
        nextBooking.setBookerId(booking.getBooker().getId());
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
            throw new IncorrectAddCommentException();
        }
        for (Booking booking : bookingList) {
            if (booking.getStatus().equals(BookingStatus.REJECTED)) {
                throw new IncorrectAddCommentException();
            }
        }
    }

    public boolean isStateCorrect(String state) {
        boolean b = Arrays.stream(State.values()).anyMatch(s -> s.toString().equals(state));
        if (!b) {
            throw new IncorrectStateException();
        }
        System.out.println(b);
        return b;


    }

}
