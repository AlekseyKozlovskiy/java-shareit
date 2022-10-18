package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.practicum.shareit.item.util.CommentDtoCreater;
import ru.practicum.shareit.item.util.ItemDtoCreater;

import java.net.URI;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc
public
class ItemControllerTest {
    @MockBean
    ItemService itemService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void add() throws Exception {
        var itemDto = ItemDtoCreater.getItemDto();
        mockMvc.perform(post("/items").content(asJsonString(itemDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(1, itemDto.getId());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void upgrade() throws Exception {
        var itemDto = ItemDtoCreater.getItemDto();

        when(itemService.upgradeItem(1L, itemDto, 1L)).thenReturn(itemDto);
        mockMvc.perform(patch(URI.create("/items/1"))
                        .content(asJsonString(itemDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void get2() throws Exception {
        var itemDto = ItemDtoCreater.getItemDto();
        when(itemService.get(1L, 1L)).thenReturn(itemDto);
        String url = String.format("/items/%d", itemDto.getId());
        MvcResult mvcResult = mockMvc.perform(get(url).header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk()).andReturn();

        Assertions.assertTrue(mvcResult.getResponse()
                .getContentAsString().contains("item name"));
    }

    @Test
    void getAll() throws Exception {
        when(itemService.getAll(1L))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void search() throws Exception {
        when(itemService.search("text"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/items/search").param("text", "text"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void addComment() throws Exception {
        var commentDto = CommentDtoCreater.getCommentDto();
        when(itemService.addComment(1L, commentDto, 1L)).thenReturn(commentDto);
        mockMvc.perform(post("/items/1/comment").content(asJsonString(commentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(1, commentDto.getId());
    }
}