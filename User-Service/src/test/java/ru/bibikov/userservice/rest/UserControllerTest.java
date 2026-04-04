package ru.bibikov.userservice.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.bibikov.userservice.dto.SaveResponse;
import ru.bibikov.userservice.entity.User;
import ru.bibikov.userservice.service.UserService;
import ru.bibikov.userservice.util.DataUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService service;


    @Test
    void getUserByIdSuccess() throws Exception {
        User user = DataUtils.getFrankPersisted();
        SaveResponse rs = new SaveResponse(true, "User found");
        when(service.findById(anyLong())).thenReturn(rs);

        mockMvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User found"));


        verify(service).findById(anyLong());
    }

    @Test
    void getUserByIdBad() throws Exception {
        User user = DataUtils.getFrankPersisted();
        SaveResponse rs = new SaveResponse(false, "User not found");
        when(service.findById(anyLong())).thenReturn(rs);

        mockMvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User not found"));
    }

    @Test
    void getUserSuccess() throws Exception {
        User user = DataUtils.getFrankPersisted();
        when(service.findUserById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/users/us/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));


        verify(service).findUserById(anyLong());
    }

    @Test
    void getUserBad() throws Exception {
        SaveResponse rs = new SaveResponse(false, "User not found");
        when(service.findUserById(anyLong())).thenReturn(rs);

        mockMvc.perform(get("/users/us/" + 1L))
                .andExpect(status().is(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User not found"));
    }

    @Test
    void saveEntitySuccess() throws Exception {
        User user = DataUtils.getFrankPersisted();
        SaveResponse rs = new SaveResponse(true, "User has been saved");
        when(service.save(eq(user))).thenReturn(rs);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User has been saved"));

        verify(service).save(user);
    }

    @Test
    void saveEntityBad() throws Exception {
        User user = DataUtils.getFrankPersisted();
        SaveResponse rs = new SaveResponse(false, "User not saved");
        when(service.save(eq(user))).thenReturn(rs);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User not saved"));

        verify(service).save(user);
    }

    @Test
    void updateUserSuccess() throws Exception {
        User userUpdate = DataUtils.getFrankPersisted();
        SaveResponse rs = new SaveResponse(true, "User has been updated");
        when(service.updateUser(eq(userUpdate), anyLong())).thenReturn(rs);

        mockMvc.perform(post("/users/" + userUpdate.getId() + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User has been updated"));

        verify(service).updateUser(eq(userUpdate), anyLong());
    }

    @Test
    void updateUserBad() throws Exception {
        User userUpdate = DataUtils.getFrankPersisted();
        SaveResponse rs = new SaveResponse(false, "User not updated");
        when(service.updateUser(eq(userUpdate), anyLong())).thenReturn(rs);

        mockMvc.perform(post("/users/" + userUpdate.getId() + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User not updated"));

        verify(service).updateUser(eq(userUpdate), anyLong());
    }

    @Test
    void deleteUserSuccess() throws Exception {
        SaveResponse rs = new SaveResponse(true, "User has been deleted");
        when(service.deleteUser(anyLong())).thenReturn(rs);

        mockMvc.perform(post("/users/" + 1L + "/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User has been deleted"));

        verify(service).deleteUser(anyLong());
    }

    @Test
    void deleteUserBad() throws Exception {
        SaveResponse rs = new SaveResponse(false, "User not deleted");
        when(service.deleteUser(anyLong())).thenReturn(rs);

        mockMvc.perform(post("/users/" + 1L + "/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User not deleted"));

        verify(service).deleteUser(anyLong());
    }
}