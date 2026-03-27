package ru.bibikov.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.bibikov.order.dto.SaveResponse;
import ru.bibikov.order.dto.User;
import ru.bibikov.order.entity.Order;
import ru.bibikov.order.repository.OrderRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public SaveResponse saveOrderByUserId(User user, Integer id) {
        //validateOrderAndUserId(order, userId);

        //validateResponse(response);

        Order order=orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found"));
        order.setUserId(user.id());
        orderRepository.save(order);
        return new SaveResponse(true, "order save");
    }

    public ResponseEntity<Order> findOrderById(Integer id) {
        validateId(id);
        Optional<Order> order = orderRepository.findById(id);
        validateOrderIsNull(order);
        return new ResponseEntity<>(order.get(), HttpStatus.FOUND);
    }

    @Transactional
    public SaveResponse updateById(Order newOrder,Integer id){
        validateId(id);
        Order order = orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found in service"));
        if (newOrder.getOrderName()!=null){
            order.setOrderName(newOrder.getOrderName());
        }
        if (newOrder.getQuantityOrder()!=null) {
            order.setQuantityOrder(newOrder.getQuantityOrder());
        }
        if (newOrder.getUnitPrice()!=null) {
            order.setUnitPrice(newOrder.getUnitPrice());
        }
        if (newOrder.getQuantityHave()!=null) {
            order.setQuantityHave(newOrder.getQuantityHave());
        }
        if (newOrder.getUserId()!=null) {
            order.setUserId(newOrder.getUserId());
        }
        return new SaveResponse(true,"Order has been updated");
    }

    public SaveResponse deleteById(Integer id){
        validateId(id);
        orderRepository.deleteById(id);
        return new SaveResponse(true,"Order has been deleted");
    }

    public SaveResponse saveOrder(Order order) {
        validateOrderIsNull(order);
        orderRepository.save(order);
        return new SaveResponse(true,"Order has been saved");
    }



    private void validateOrderAndUserId(Order order, Long userId) {
        log.info("validate {}, : {}", order, userId);
        if (order == null && userId == null) {
            throw new RuntimeException("Order or user id is null");
        }
    }
    private void validateOrderIsNull(Optional<Order> order){
        if (order.isEmpty()) throw new RuntimeException();
    }
    private void validateOrderIsNull(Order order){
        if (order==null) throw new RuntimeException("Order is null");
    }
    private void validateResponse(ResponseEntity<?> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("User not found");
        }
    }

    private void validateId(Integer id){
        if(id==null||id.equals(0))throw new RuntimeException("Id must not be empty");
    }
}
