package ru.practicum.shareit.booking;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {

        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(ItemMapper.toItemDto(booking.getItem()))
                .booker(UserMapper.toUserDto(booking.getBooker()))
                .status(booking.getStatus())
                .approved(booking.getApproved())
                .status(booking.getStatus())
                .build();
    }

    public static Booking toNewBooking(BookingDto bookingDto) {
        Booking booking = new Booking();

        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setBooker(UserMapper.toNewUser(bookingDto.getBooker()));
        booking.setStatus(bookingDto.getStatus());
        ItemDto item = bookingDto.getItem();
        booking.setItem(ItemMapper.toNewItem(item));

        return booking;
    }

    public static List<BookingDto> toBookingDtoList(List<Booking> bookings) {
        List<BookingDto> bookingDtoList = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingDtoList.add(toBookingDto(booking));
        }
        return bookingDtoList;
    }

    public static BookingDtoCreate toBookingDtoCreate(Booking booking) {

        return BookingDtoCreate.builder()
                .id(booking.getId())
                .build();
    }
}
