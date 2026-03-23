package ru.bibikov.order.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bibikov.order.dto.SaveResponse;
import ru.bibikov.order.dto.User;
import ru.bibikov.order.entity.Order;
import ru.bibikov.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final OrderService service;

    @PostMapping()
    public ResponseEntity<SaveResponse> saveEntity(@RequestBody Order order){
        log.info("save {}",order);
        return ResponseEntity.ok(service.saveOrderByUserId(order));
    }

}
