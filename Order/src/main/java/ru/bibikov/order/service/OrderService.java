package ru.bibikov.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.bibikov.order.dto.SaveResponse;
import ru.bibikov.order.dto.User;
import ru.bibikov.order.entity.Order;
import ru.bibikov.order.exception.OrderNotFoundException;
import ru.bibikov.order.repository.OrderRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public SaveResponse saveOrder(Order order) {
        validateOrderIsNull(order);
        orderRepository.save(order);
        return new SaveResponse(true, "Order has been saved");
    }

    public SaveResponse saveOrderByUserId(User user, Integer id) {
        validateOrderAndUserId(user, id);

        try {
            Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
            order.setUserId(user.id());
            orderRepository.save(order);
            return new SaveResponse(true, "order save");
        }catch (OrderNotFoundException e){
            return new SaveResponse(false,"order not save");
        }
    }

    public Object findOrderById(Integer id) {
        try{
            validateId(id);
            Optional<Order> order = orderRepository.findById(id);
            validateOrderIsNull(order);
            return order.get();
        }catch (RuntimeException e){
            SaveResponse rs=new SaveResponse(false,"Order not found");
            return rs;
        }
    }


    public SaveResponse updateById(Order newOrder, Integer id) {
        validateId(id);
        try {
            Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found in service"));
            updateOrderCode(newOrder, order);
            orderRepository.save(order);
            return new SaveResponse(true, "Order has been updated");
        }catch (OrderNotFoundException e){
            return new SaveResponse(false, "Order not found");
        }
    }

    public SaveResponse deleteById(Integer id) {
        validateId(id);
        if (!orderRepository.existsById(id)){
            return new SaveResponse(false,"Order not found");
        }
        orderRepository.deleteById(id);
        return new SaveResponse(true, "Order has been deleted");
    }


    private void validateOrderAndUserId(User user, Integer id) {
        log.info("validate {}, : {}", user, id);
        if (user == null && id == null) {
            throw new RuntimeException("User or order id is null");
        }
    }

    private void validateOrderIsNull(Optional<Order> order) {
        if (order.isEmpty()) throw new RuntimeException();
    }

    private void validateOrderIsNull(Order order) {
        if (order == null) throw new RuntimeException("Order is null");
    }

    private void validateId(Integer id) {
        if (id == null || id.equals(0)) throw new RuntimeException("Id must not be empty");
    }

    private void updateOrderCode(Order newOrder, Order order) {
        if (newOrder.getOrderName() != null) {
            order.setOrderName(newOrder.getOrderName());
        }
        if (newOrder.getQuantityOrder() != null) {
            order.setQuantityOrder(newOrder.getQuantityOrder());
        }
        if (newOrder.getUnitPrice() != null) {
            order.setUnitPrice(newOrder.getUnitPrice());
        }
        if (newOrder.getQuantityHave() != null) {
            order.setQuantityHave(newOrder.getQuantityHave());
        }
        if (newOrder.getUserId() != null) {
            order.setUserId(newOrder.getUserId());
        }
    }
}
