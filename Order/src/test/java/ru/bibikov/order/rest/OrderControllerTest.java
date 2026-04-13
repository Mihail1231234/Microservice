package ru.bibikov.order.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.bibikov.order.dto.SaveResponse;
import ru.bibikov.order.dto.User;
import ru.bibikov.order.entity.Order;
import ru.bibikov.order.service.OrderService;
import ru.bibikov.order.util.DataUtils;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void saveEntitySuccess() throws Exception {
        Order order = DataUtils.getIphonePersisted();
        User user = DataUtils.getUserTransient();
        SaveResponse rs = new SaveResponse(true, "Ok");

        when(orderService.saveOrderByUserId(eq(user), anyInt())).thenReturn(rs);

        mockMvc.perform(post("/orders/order-userId/" + order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Ok"));
    }

    @Test
    void saveEntityBad() throws Exception {
        Order order = DataUtils.getIphonePersisted();
        User user = DataUtils.getUserTransient();
        SaveResponse rs = new SaveResponse(false, "Bad");

        when(orderService.saveOrderByUserId(eq(user), anyInt())).thenReturn(rs);

        mockMvc.perform(post("/orders/order-userId/" + order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Bad"));
    }

    @Test
    void saveOrderSuccess() throws Exception {
        Order order = DataUtils.getIphoneTransient();
        SaveResponse rs = new SaveResponse(true, "Ok");

        when(orderService.saveOrder(any(Order.class))).thenReturn(rs);


        mockMvc.perform(post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Ok"));
    }

    @Test
    void saveOrderBad() throws Exception {
        Order order = DataUtils.getIphonePersisted();
        SaveResponse rs = new SaveResponse(false, "Bad");

        when(orderService.saveOrder(any(Order.class))).thenReturn(rs);


        mockMvc.perform(post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Bad"));
        verify(orderService).saveOrder(order);
    }

    @Test
    void getOrderSuccess() throws Exception {
        Order order = DataUtils.getIphonePersisted();

        when(orderService.findOrderById(order.getId())).thenReturn(order);

        mockMvc.perform(get("/orders/" + order.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.unitPrice").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantityOrder").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderName").value("Iphone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantityHave").value(15));
    }

    @Test
    void getOrderBad() throws Exception {
        SaveResponse rs = new SaveResponse(false, "Order not found");
        when(orderService.findOrderById(1))
                .thenReturn(rs);
        mockMvc.perform(get("/orders/1"))
                .andExpect(status().is(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order not found"));
    }

    @Test
    void updateOrderSuccess() throws Exception {
        Order order = DataUtils.getIphonePersisted();
        SaveResponse rs = new SaveResponse(true, "Order has been updated");

        when(orderService.updateById(eq(order), anyInt())).thenReturn(rs);

        mockMvc.perform(post("/orders/" + order.getId() + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order has been updated"));
    }

    @Test
    void updateOrderBad() throws Exception {
        Order order = DataUtils.getIphonePersisted();
        SaveResponse rs = new SaveResponse(false, "Order not found");

        when(orderService.updateById(eq(order), anyInt())).thenReturn(rs);

        mockMvc.perform(post("/orders/" + order.getId() + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order not found"));

        verify(orderService).updateById(eq(order), anyInt());
    }


    @Test
    void deleteOrderSuccess() throws Exception {
        SaveResponse rs = new SaveResponse(true, "Order has been deleted");
        when(orderService.deleteById(anyInt())).thenReturn(rs);

        mockMvc.perform(post("/orders/" + 1 + "/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order has been deleted"));
        verify(orderService).deleteById(1);
    }


    @Test
    void deleteOrderBad() throws Exception {
        SaveResponse rs = new SaveResponse(false, "Order not found");
        when(orderService.deleteById(anyInt())).thenReturn(rs);

        mockMvc.perform(post("/orders/" + anyInt() + "/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order not found"));
    }
}