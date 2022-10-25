package ru.practicum.shareit.booking.util;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

public class BookingDtoCreater {
    public static BookingDto getBookingDto() {
        User user = new User(1L, "user 1", "user1@email");
        Item item = new Item(1L, "item name", "description", true, user, null);
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setItem(item);
        booking.setBooker(user);
        return BookingMapper.toBookingDto(booking);
    }

}
