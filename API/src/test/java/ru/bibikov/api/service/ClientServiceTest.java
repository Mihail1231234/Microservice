package ru.bibikov.api.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import ru.bibikov.api.dto.Order;
import ru.bibikov.api.dto.SaveResponse;
import ru.bibikov.api.dto.User;
import ru.bibikov.api.utils.DataUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ClientService service;

    @BeforeEach
    void initMocks(){
        mockServiceUrls();
    }
    private void mockServiceUrls(){
        ReflectionTestUtils.setField(service, "urlUser","http://localhost:8081/users");
        ReflectionTestUtils.setField(service, "urlOrder","http://localhost:8084/orders");
    }

    @Test
    void sendUserSuccess() {
        User user= DataUtils.getJohnTransient();
        SaveResponse rs=new SaveResponse(true,"User saved");

        when(restTemplate.postForEntity(anyString(),eq(user),eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(rs, HttpStatus.OK));

        SaveResponse actual=service.sendUser(user);

        assertNotNull(actual);
        assertTrue(actual.success());
        assertEquals("User saved",actual.message());
        verify(restTemplate).postForEntity(anyString(),eq(user),eq(SaveResponse.class));
    }

    @Test
    void sendUserBad() {
        User user= DataUtils.getJohnTransient();
        SaveResponse rs=new SaveResponse(false,"User not saved");

        when(restTemplate.postForEntity(anyString(),eq(user),eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(rs, HttpStatus.OK));

        SaveResponse actual=service.sendUser(user);

        assertNotNull(actual);
        assertFalse(actual.success());
        assertEquals("User not saved",actual.message());
        verify(restTemplate).postForEntity(anyString(),eq(user),eq(SaveResponse.class));
    }

    @Test
    void getUserByIdSuccess() {
        User user=DataUtils.getFrankPersisted();

        when(restTemplate.getForEntity(anyString(),eq(Object.class)))
                .thenReturn(new ResponseEntity<>(user,HttpStatus.OK));

        Object obj=service.getUserById(1L);
        User userFromService=(User)obj;

        assertNotNull(userFromService);
        assertEquals("Frank",userFromService.name());
        assertEquals("frank@gmail.com",userFromService.email());
        verify(restTemplate).getForEntity(anyString(),eq(Object.class));
    }
    @Test
    void getUserByIdBad() {
        SaveResponse example=new SaveResponse(false,"User not found");

        when(restTemplate.getForEntity(anyString(), eq(Object.class)))
                .thenReturn(new ResponseEntity<>(example, HttpStatus.OK));

        Object obj=service.getUserById(1L);
        SaveResponse rs=(SaveResponse) obj;

        assertNotNull(rs);
        assertFalse(rs.success());
        assertEquals("User not found",rs.message());
        verify(restTemplate).getForEntity(anyString(), eq(Object.class));
    }

    @Test
    void updateUserSuccess() {
        User user=DataUtils.getFrankPersisted();
        SaveResponse example=new SaveResponse(true,"User has been updated");

        when(restTemplate.postForEntity(anyString(),eq(user),eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        SaveResponse rs=service.updateUser(user,1L);

        assertNotNull(rs);
        assertTrue(rs.success());
        assertEquals("User has been updated",rs.message());
        verify(restTemplate).postForEntity(anyString(),eq(user),eq(SaveResponse.class));
    }

    @Test
    void updateUserBad() {
        User user=DataUtils.getFrankPersisted();
        SaveResponse example=new SaveResponse(false,"User not updated");

        when(restTemplate.postForEntity(anyString(),eq(user),eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        SaveResponse rs=service.updateUser(user,1L);

        assertNotNull(rs);
        assertFalse(rs.success());
        assertEquals("User not updated",rs.message());
        verify(restTemplate).postForEntity(anyString(),eq(user),eq(SaveResponse.class));
    }

    @Test
    void deleteUserSuccess() {
        SaveResponse example=new SaveResponse(true,"User has been updated");

        when(restTemplate.postForEntity(anyString(),eq(null),eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        SaveResponse rs =service.deleteUser(1L);

        assertNotNull(rs);
        assertTrue(rs.success());
        assertEquals("User has been updated",rs.message());
        verify(restTemplate).postForEntity(anyString(),eq(null),eq(SaveResponse.class));
    }

    @Test
    void deleteUserBad() {
        SaveResponse example=new SaveResponse(false,"User not updated");

        when(restTemplate.postForEntity(anyString(),eq(null),eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        SaveResponse rs =service.deleteUser(1L);

        assertNotNull(rs);
        assertFalse(rs.success());
        assertEquals("User not updated",rs.message());
        verify(restTemplate).postForEntity(anyString(),eq(null),eq(SaveResponse.class));
    }

    @Test
    void sendUserToOrderSuccess() {
        User user=DataUtils.getFrankPersisted();
        SaveResponse example=new SaveResponse(true,"ok");

        when(restTemplate.getForEntity(anyString(), eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));
        when(restTemplate.postForEntity(anyString(),eq(user), eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        SaveResponse rs=service.sendUserToOrder(user,1);

        assertNotNull(rs);
        assertTrue(rs.success());
        assertEquals("ok",rs.message());
        verify(restTemplate).getForEntity(anyString(),eq(SaveResponse.class));
        verify(restTemplate).postForEntity(anyString(),eq(user), eq(SaveResponse.class));
    }

    @Test
    void sendUserToOrderBad() {
        User user=DataUtils.getFrankPersisted();
        SaveResponse example=new SaveResponse(false ,"bad");

        when(restTemplate.getForEntity(anyString(), eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        SaveResponse rs=service.sendUserToOrder(user,1);

        assertNotNull(rs);
        assertFalse(rs.success());
        assertEquals("User not found",rs.message());
        verify(restTemplate).getForEntity(anyString(),eq(SaveResponse.class));
    }

    @Test
    void saveOrderSuccess() {
        Order order=DataUtils.getIphoneTransient();
        SaveResponse example=new SaveResponse(true,"User has been saved");

        when(restTemplate.postForEntity(anyString(),eq(order),eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        SaveResponse rs=service.saveOrder(order);

        assertNotNull(rs);
        assertTrue(rs.success());
        assertEquals("User has been saved",rs.message());
        verify(restTemplate).postForEntity(anyString(),eq(order),eq(SaveResponse.class));
    }

    @Test
    void saveOrderFalse() {
        Order order=DataUtils.getIphoneTransient();
        SaveResponse example=new SaveResponse(false,"User not saved");

        when(restTemplate.postForEntity(anyString(),eq(order),eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        SaveResponse rs=service.saveOrder(order);

        assertNotNull(rs);
        assertFalse(rs.success());
        assertEquals("User not saved",rs.message());
        verify(restTemplate).postForEntity(anyString(),eq(order),eq(SaveResponse.class));
    }

    @Test
    void getOrderByIdSuccess() {
        Order example=DataUtils.getIphoneTransient();

        when(restTemplate.getForEntity(anyString(),eq(Object.class)))
                .thenReturn(new ResponseEntity<>((Object) example,HttpStatus.OK));

        Object objOrder=service.getOrderById(1);
        Order order=(Order) objOrder;

        assertNotNull(order);
        assertEquals(1L,order.userId());
        assertEquals(500,order.unitPrice());
        assertEquals(1,order.quantityOrder());
        assertEquals("Iphone",order.orderName());
        assertEquals(15,order.quantityHave());
        verify(restTemplate).getForEntity(anyString(),eq(Object.class));
    }
    @Test
    void getOrderByIdBad() {
        SaveResponse example=new SaveResponse(false,"Order not found");

        when(restTemplate.getForEntity(anyString(),eq(Object.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        Object objOrder=service.getOrderById(1);
        SaveResponse rs=(SaveResponse) objOrder;

        assertNotNull(rs);
        assertFalse(rs.success());
        assertEquals("Order not found",rs.message());
        verify(restTemplate).getForEntity(anyString(),eq(Object.class));
    }

    @Test
    void updateOrderSuccess() {
        Order order=DataUtils.getIphoneTransient();
        SaveResponse example=new SaveResponse(true,"Order has been updated");

        when(restTemplate.postForEntity(anyString(),eq(order),eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        SaveResponse rs=service.updateOrder(order,1);

        assertNotNull(rs);
        assertTrue(rs.success());
        assertEquals("Order has been updated",rs.message());
        verify(restTemplate).postForEntity(anyString(),eq(order),eq(SaveResponse.class));
    }

    @Test
    void updateOrderBad() {
        Order order=DataUtils.getIphoneTransient();
        SaveResponse example=new SaveResponse(false,"Order not updated");

        when(restTemplate.postForEntity(anyString(),eq(order),eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        SaveResponse rs=service.updateOrder(order,1);

        assertNotNull(rs);
        assertFalse(rs.success());
        assertEquals("Order not updated",rs.message());
        verify(restTemplate).postForEntity(anyString(),eq(order),eq(SaveResponse.class));
    }

    @Test
    void deleteOrderSuccess() {
        SaveResponse example=new SaveResponse(true,"Order has been deleted");

        when(restTemplate.postForEntity(anyString(),eq(null),eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        SaveResponse rs=service.deleteOrder(1);

        assertNotNull(rs);
        assertTrue(rs.success());
        assertEquals("Order has been deleted",rs.message());
        verify(restTemplate).postForEntity(anyString(),eq(null),eq(SaveResponse.class));
    }
    @Test
    void deleteOrderBad() {
        SaveResponse example=new SaveResponse(false,"Order not deleted");

        when(restTemplate.postForEntity(anyString(),eq(null),eq(SaveResponse.class)))
                .thenReturn(new ResponseEntity<>(example,HttpStatus.OK));

        SaveResponse rs=service.deleteOrder(1);

        assertNotNull(rs);
        assertFalse(rs.success());
        assertEquals("Order not deleted",rs.message());
        verify(restTemplate).postForEntity(anyString(),eq(null),eq(SaveResponse.class));
    }
}