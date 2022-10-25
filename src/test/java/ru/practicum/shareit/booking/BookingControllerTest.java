package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.util.BookingDtoCreater;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.practicum.shareit.item.ItemControllerTest.asJsonString;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {


    @Autowired
    MockMvc mockMvc;
    @MockBean
    BookingService bookingService;
    private User user = new User(1L, "Simple User", "user@mail.ru");
    private User user2 = new User(2L, "Another User", "test@mail.ru");
    private Item item = new Item(1L, "item name", "Super unit", true, user, null);
    private Booking booking = new Booking(1L, LocalDateTime.now(), LocalDateTime.now().plusHours(2L),
            item, user2, BookingStatus.WAITING, true, false);

    @Test
    void addItemRequest() throws Exception {
        var bookingDto = BookingDtoCreater.getBookingDto();
        mockMvc.perform(post("/bookings").content(asJsonString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void upgradeNewItem() throws Exception {
        booking.setStatus(BookingStatus.APPROVED);
        Booking booking1 = booking;
        BookingDto bookingDto = BookingMapper.toBookingDto(booking1);
        when(bookingService.upgrade(anyLong(), anyLong(), anyBoolean())).thenReturn(bookingDto);
        mockMvc.perform(patch("/bookings/{bookingId}", booking.getId())
                        .param("approved", "true")
                        .header("X-Sharer-User-Id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(BookingStatus.APPROVED.toString())));
    }

    @Test
    void get2() throws Exception {
        var bookingDto = BookingDtoCreater.getBookingDto();
        System.out.println(bookingDto);
        when(bookingService.get(anyLong(), anyLong(), anyLong(), anyLong())).thenReturn(bookingDto);
        mockMvc.perform(get(URI.create("/bookings/1"))
                        .param("from", "1")
                        .param("size", "1")
                        .header("X-Sharer-User-Id", user.getId()))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.item.name", is(booking.getItem().getName())));
    }

    @Test
    void getAll() throws Exception {
        when(bookingService.getAll(1L, "All", PageRequest.ofSize(10)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllOfOwner() throws Exception {
        when(bookingService.getAllOfOwner(1L, "All", PageRequest.ofSize(10)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/bookings/owner"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}