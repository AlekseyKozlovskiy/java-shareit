package ru.practicum.shareit.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class ItemRequestControllerTest extends ShareItTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemRequestService itemRequestService;
    private User user = new User(1L, "Simple User", "user@mail.ru");
    private ItemRequest itemRequest = new ItemRequest(1L, "text", user, LocalDateTime.now());

    @Test
    void addItemRequest() throws Exception {
        ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(itemRequest);
        when(itemRequestService.add(anyLong(), any()))
                .thenReturn(ItemRequestMapper.toItemRequestDto(itemRequest));
        mockMvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequestDto))
                        .header("X-Sharer-User-Id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.description", is(itemRequest.getDescription())));
    }

    @Test
    void get2() throws Exception {
        when(itemRequestService.get(anyLong()))
                .thenReturn(List.of(ItemRequestMapper.toItemRequestDto(itemRequest)));
        mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[0].description", is(itemRequest.getDescription())));
    }

    @Test
    void getAll() throws Exception {
        when(itemRequestService.getAll(anyLong(), anyLong(), anyLong()))
                .thenReturn(List.of(ItemRequestMapper.toItemRequestDto(itemRequest)));
        mockMvc.perform(get("/requests/all")
                        .param("from", "1")
                        .param("size", "1")
                        .header("X-Sharer-User-Id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[0].description", is(itemRequest.getDescription())));
    }

    @Test
    void getById() throws Exception {
        when(itemRequestService.getById(anyLong(), anyLong()))
                .thenReturn(ItemRequestMapper.toItemRequestDto(itemRequest));
        mockMvc.perform(get("/requests/{itemRequestId}", itemRequest.getId())
                        .header("X-Sharer-User-Id", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.description", is(itemRequest.getDescription())));
    }
}