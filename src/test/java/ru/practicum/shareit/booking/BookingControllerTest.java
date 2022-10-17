package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.practicum.shareit.booking.util.BookingDtoCreater;

import java.net.URI;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc
class BookingControllerTest {

    @MockBean
    BookingService bookingService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void addItemRequest() throws Exception {
        var bookingDto = BookingDtoCreater.getBookingDto();
        mockMvc.perform(post("/bookings").content(asJsonString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

//        assertEquals(1, itemDto.getId());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void upgradeNewItem() throws Exception {
        var bookingDto = BookingDtoCreater.getBookingDto();

        when(bookingService.upgrade(1L, 1L, true)).thenReturn(bookingDto);
        mockMvc.perform(patch(URI.create("/bookings/1"))
                        .content(asJsonString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void get2() throws Exception {
        var bookingDto = BookingDtoCreater.getBookingDto();

        when(bookingService.get(1L, 1L, 1L, 1L)).thenReturn(bookingDto);
        String url = String.format("/bookings/%d", bookingDto.getId());
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk()).andReturn();

        verify(bookingService, times(1))
                .get(null, 1L, null, null);
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