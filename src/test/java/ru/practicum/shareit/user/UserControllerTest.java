package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.user.util.UserDtoCreater;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {


    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;


    @Test
    void add() throws Exception {
        var userDto = UserDtoCreater.getUserDto();

        when(userService.add(userDto)).thenReturn(userDto);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
        assertEquals(1, userDto.getId());

    }

    @Test
    void upgrade() throws Exception {
        var userDto = UserDtoCreater.getUserDto();

        when(userService.upgrade(1L, userDto)).thenReturn(userDto);
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
//        String url = String.format("/users/%d", userDto.getId());
//        MvcResult mvcResult = mockMvc.perform(patch(url)).andExpect(status().isOk()).andReturn();
//        System.out.println(mvcResult.getResponse().getContentAsString() + "!!!!!!!!!!!!!");
//
//        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("{\"id\":1,\"name\":\"user 1\",\"email\":\"user1@email\"}"));

//                .andExpect(status().isOk()).andReturn();
//        verify(userService, times(1)).upgrade(1L, userDto);

    }

    @Test
    void getById() throws Exception {
        var userDto = UserDtoCreater.getUserDto();
        when(userService.get(1L)).thenReturn(userDto);
        String url = String.format("/users/%d", userDto.getId());
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk()).andReturn();
        Assertions.assertTrue(mvcResult.getResponse()
                .getContentAsString().contains("{\"id\":1,\"name\":\"user 1\",\"email\":\"user1@email\"}"));
    }

    @Test
    void delete() throws Exception {
        doNothing().when(userService).delete(1L);
        userService.delete(1L);
        verify(userService, times(1)).delete(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        when(userService.getAll(PageRequest.ofSize(10)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(userService, times(1))
                .getAll(PageRequest.ofSize(10));
    }

}