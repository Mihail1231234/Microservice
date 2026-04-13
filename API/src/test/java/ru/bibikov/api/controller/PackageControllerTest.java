package ru.bibikov.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.bibikov.api.dto.Order;
import ru.bibikov.api.dto.SaveResponse;
import ru.bibikov.api.dto.User;
import ru.bibikov.api.service.ClientService;
import ru.bibikov.api.utils.DataUtils;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService service;

    @Test
    void sendUserSuccess() throws Exception {
        User user = DataUtils.getFrankPersisted();
        SaveResponse example = new SaveResponse(true, "User has been send");
        Mockito.when(service.sendUser(eq(user))).thenReturn(example);

        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User has been send"));

        verify(service).sendUser(eq(user));
    }

    @Test
    void sendUserBad() throws Exception {
        User user = DataUtils.getFrankPersisted();
        SaveResponse example = new SaveResponse(false, "User not send");
        Mockito.when(service.sendUser(eq(user))).thenReturn(example);

        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User not send"));

        verify(service).sendUser(eq(user));
    }

    @Test
    void getUserSuccess() throws Exception {
        User example = DataUtils.getFrankPersisted();

        Mockito.when(service.getUserById(anyLong())).thenReturn(example);

        mockMvc.perform(get("/client/user/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Frank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("frank@gmail.com"));
        verify(service).getUserById(anyLong());
    }

    @Test
    void getUserBad() throws Exception {
        SaveResponse example = new SaveResponse(false, "User not found");

        Mockito.when(service.getUserById(anyLong())).thenReturn(example);

        mockMvc.perform(get("/client/user/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User not found"));
        verify(service).getUserById(anyLong());
    }

    @Test
    void updateUserSuccess() throws Exception {
        User user = DataUtils.getFrankPersisted();
        SaveResponse rs = new SaveResponse(true, "User has been updated");
        Mockito.when(service.updateUser(eq(user), anyLong())).thenReturn(rs);

        mockMvc.perform(post("/client/user/" + 1 + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User has been updated"));

        verify(service).updateUser(eq(user), anyLong());
    }

    @Test
    void updateUserBad() throws Exception {
        User user = DataUtils.getFrankPersisted();
        SaveResponse rs = new SaveResponse(false, "User not updated");
        Mockito.when(service.updateUser(eq(user), anyLong())).thenReturn(rs);

        mockMvc.perform(post("/client/user/" + 1 + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User not updated"));

        verify(service).updateUser(eq(user), anyLong());
    }

    @Test
    void deleteUserSuccess() throws Exception {
        SaveResponse rs = new SaveResponse(true, "User has been deleted");

        Mockito.when(service.deleteUser(anyLong())).thenReturn(rs);

        mockMvc.perform(post("/client/user/" + anyLong() + "/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User has been deleted"));

        verify(service).deleteUser(anyLong());
    }

    @Test
    void deleteUserBad() throws Exception {
        SaveResponse rs = new SaveResponse(false, "User not deleted");

        Mockito.when(service.deleteUser(anyLong())).thenReturn(rs);

        mockMvc.perform(post("/client/user/" + anyLong() + "/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User not deleted"));

        verify(service).deleteUser(anyLong());
    }

    @Test
    void sendUserToOrderSuccess() throws Exception {
        User user = DataUtils.getFrankPersisted();
        SaveResponse rs = new SaveResponse(true, "User has been send");

        Mockito.when(service.sendUserToOrder(eq(user), anyInt())).thenReturn(rs);

        mockMvc.perform(post("/client/order/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User has been send"));

        verify(service).sendUserToOrder(eq(user), anyInt());
    }

    @Test
    void sendUserToOrderBad() throws Exception {
        User user = DataUtils.getFrankPersisted();
        SaveResponse rs = new SaveResponse(false, "User not send");

        Mockito.when(service.sendUserToOrder(eq(user), anyInt())).thenReturn(rs);

        mockMvc.perform(post("/client/order/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User not send"));

        verify(service).sendUserToOrder(eq(user), anyInt());
    }

    @Test
    void sendOrderSuccess() throws Exception {
        Order order = DataUtils.getIphoneTransient();
        SaveResponse rs = new SaveResponse(true, "Order has been saved");

        Mockito.when(service.saveOrder(eq(order))).thenReturn(rs);

        mockMvc.perform(post("/client/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Order has been saved"));

        verify(service).saveOrder(eq(order));
    }

    @Test
    void sendOrderBad() throws Exception {
        Order order = DataUtils.getIphoneTransient();
        SaveResponse rs = new SaveResponse(false, "Order not saved");

        Mockito.when(service.saveOrder(eq(order))).thenReturn(rs);

        mockMvc.perform(post("/client/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Order not saved"));

        verify(service).saveOrder(eq(order));
    }

    @Test
    void getOrderSuccess() throws Exception {
        Order example = DataUtils.getIphonePersisted();

        Mockito.when(service.getOrderById(anyInt())).thenReturn(example);

        mockMvc.perform(get("/client/order/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.unitPrice").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantityOrder").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderName").value("Iphone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantityHave").value(15));

        verify(service).getOrderById(anyInt());
    }

    @Test
    void getOrderBad() throws Exception {
        SaveResponse rs = new SaveResponse(false, "Order with this id not found");

        Mockito.when(service.getOrderById(anyInt())).thenReturn(rs);

        mockMvc.perform(get("/client/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Order with this id not found"));

        verify(service).getOrderById(anyInt());
    }

    @Test
    void updateOrderSuccess() throws Exception {
        Order order = DataUtils.getIphoneTransient();
        SaveResponse rs = new SaveResponse(true, "Order has been updated");

        Mockito.when(service.updateOrder(eq(order), anyInt())).thenReturn(rs);

        mockMvc.perform(post("/client/order/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Order has been updated"));

        verify(service).updateOrder(eq(order),anyInt());
    }

    @Test
    void updateOrderBad() throws Exception {
        Order order = DataUtils.getIphoneTransient();
        SaveResponse rs = new SaveResponse(false, "Order not updated");

        Mockito.when(service.updateOrder(eq(order), anyInt())).thenReturn(rs);

        mockMvc.perform(post("/client/order/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Order not updated"));

        verify(service).updateOrder(eq(order),anyInt());
    }

    @Test
    void deleteOrderSuccess() throws Exception {
        SaveResponse rs=new SaveResponse(true,"Order has been deleted");

        Mockito.when(service.deleteOrder(anyInt())).thenReturn(rs);

        mockMvc.perform(post("/client/order/1/delete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Order has been deleted"));

        verify(service).deleteOrder(anyInt());
    }
    @Test
    void deleteOrderBad() throws Exception {
        SaveResponse rs=new SaveResponse(false,"Order not deleted");

        Mockito.when(service.deleteOrder(anyInt())).thenReturn(rs);

        mockMvc.perform(post("/client/order/1/delete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Order not deleted"));

        verify(service).deleteOrder(anyInt());
    }
}