package ru.bibikov.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bibikov.order.dto.SaveResponse;
import ru.bibikov.order.entity.Order;
import ru.bibikov.order.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public SaveResponse saveOrderByUserId(Order order){
        if(order==null) {throw new RuntimeException("Order is null");}

        ResponseEntity<Integer> response=restTemplate.getForEntity("http://localhost:8081/users/{id}",
                Integer.class,
                order.getUserId());
        if (!response.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException("User not found");
        }
        Integer userId=response.getBody();
        order.setUserId(userId);
        orderRepository.save(order);
        return new SaveResponse(true,"order save");
    }
}
