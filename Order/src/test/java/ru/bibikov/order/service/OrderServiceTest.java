package ru.bibikov.order.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.bibikov.order.dto.SaveResponse;
import ru.bibikov.order.dto.User;
import ru.bibikov.order.entity.Order;
import ru.bibikov.order.repository.OrderRepository;
import ru.bibikov.order.util.DataUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void saveOrder() {
        Order order= DataUtils.getIphoneTransient();
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        SaveResponse rs = orderService.saveOrder(order);

        Assertions.assertTrue(rs.success());
        Mockito.verify(orderRepository).save(order);
    }

    @Test
    void saveOrderByUserIdSuccess() {
        Order order=DataUtils.getIphonePersistedWithoutUserId();
        User user= User.builder()
                .id(1L)
                .name("John")
                .email("john@gmail.com")
                .build();

        Mockito.when(orderRepository.save(order)).thenReturn(order);
        Mockito.when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        SaveResponse rs=orderService.saveOrderByUserId(user,order.getId());

        Assertions.assertTrue(rs.success());
        Mockito.verify(orderRepository).save(order);

    }

    @Test
    void saveOrderByUserIdBad() {
        Order order=DataUtils.getIphonePersistedWithoutUserId();
        User user= User.builder()
                .id(1L)
                .name("John")
                .email("john@gmail.com")
                .build();

        Mockito.when(orderRepository.findById(1)).thenReturn(Optional.empty());

        SaveResponse rs=orderService.saveOrderByUserId(user,1);

        Assertions.assertFalse(rs.success());
        Mockito.verify(orderRepository).findById(1);

    }

    @Test
    void findOrderByIdSuccess() {
        Order order =DataUtils.getIphoneTransient();
        Mockito.when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Object orderFromDb = orderService.findOrderById(1);
        Order order1=(Order) orderFromDb;

        Assertions.assertNotNull(order1);
        assertEquals("Iphone",order1.getOrderName());

        Mockito.verify(orderRepository).findById(1);
    }

    //Как сделать ошибочный тест в данном месте
    @Test
    void findOrderByIdBad() {
        Mockito.when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

        Object orderFromDb = orderService.findOrderById(1);
        SaveResponse rs=(SaveResponse) orderFromDb;

        Assertions.assertNotNull(rs);
        assertFalse(rs.success());


    }

    @Test
    void updateByIdSuccess() {
        Order or = DataUtils.getIphoneTransient();
        Mockito.when(orderRepository.findById(anyInt())).thenReturn(Optional.of(or));
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(Mockito.any(Order.class));

        or.setOrderName("Not Iphone");
        SaveResponse rs =orderService.updateById(or,1);

        assertNotNull(rs);
        assertTrue(rs.success());
        Mockito.verify(orderRepository).findById(anyInt());
        Mockito.verify(orderRepository).save(Mockito.any(Order.class));
    }
    @Test
    void updateByIdBad() {
        Order or = DataUtils.getIphoneTransient();
        Mockito.when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());


        or.setOrderName("Not Iphone");
        SaveResponse rs =orderService.updateById(or,1);

        assertNotNull(rs);
        assertFalse(rs.success());
        Mockito.verify(orderRepository).findById(anyInt());
    }

    @Test
    void deleteByIdSuccess() {
        Mockito.when(orderRepository.existsById(1)).thenReturn(true);

        SaveResponse rs =orderService.deleteById(1);

        assertNotNull(rs);
        assertTrue(rs.success());
        Mockito.verify(orderRepository).deleteById(1);
    }

    @Test
    void deleteByIdBad() {
        Mockito.when(orderRepository.existsById(1)).thenReturn(false);

        SaveResponse rs =orderService.deleteById(1);

        assertNotNull(rs);
        assertFalse(rs.success());
    }
}