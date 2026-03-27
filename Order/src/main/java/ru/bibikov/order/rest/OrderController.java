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

    @PostMapping("/order-userId/{id}")
    public ResponseEntity<SaveResponse> saveEntity(@RequestBody User user, @PathVariable Integer id){
        log.info("save user id {}",user.id());
        return ResponseEntity.ok(service.saveOrderByUserId(user, id));
    }
    @PostMapping("/save")
    public ResponseEntity<SaveResponse> saveOrder(@RequestBody Order order){
        log.info("Save order {}",order);
        return ResponseEntity.ok(service.saveOrder(order));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer id){
        log.info("get order by id {}",id);
        return ResponseEntity.ok(service.findOrderById(id).getBody());
    }
    @PostMapping("/{id}/update")
    public ResponseEntity<SaveResponse> updateOrder(@RequestBody Order newOrder, @PathVariable Integer id){
        log.info("update order {}",newOrder);
        SaveResponse response=service.updateById(newOrder,id);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{id}/delete")
    public ResponseEntity<SaveResponse> deleteOrder(@PathVariable Integer id){
        log.info("delete order by id {}",id);
        SaveResponse response=service.deleteById(id);
        return ResponseEntity.ok(response);
    }

}
