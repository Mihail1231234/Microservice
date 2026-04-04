package ru.bibikov.order.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.hamcrest.CoreMatchers;
import ru.bibikov.order.entity.Order;
import ru.bibikov.order.service.OrderService;
import ru.bibikov.order.util.DataUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void saveEntity() {
        /*Order order= DataUtils.getIphoneTransient();

        mockMvc.perform(post("/orders/save"));*/
    }

    /*@Test
    void saveOrder() throws Exception {
        Order order= DataUtils.getIphoneTransient();

        ResultActions result = mockMvc.perform(post("/orders/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.success").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString());
    }
*/
    @Test
    void getOrder() {
    }

    @Test
    void updateOrder() {
    }

    @Test
    void deleteOrder() {
    }
}