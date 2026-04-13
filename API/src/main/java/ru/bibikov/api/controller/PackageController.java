package ru.bibikov.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bibikov.api.dto.Order;
import ru.bibikov.api.dto.SaveResponse;
import ru.bibikov.api.dto.User;
import ru.bibikov.api.service.ClientService;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@Slf4j
public class PackageController {

    private final ClientService clientService;

    @PostMapping()
    public ResponseEntity<SaveResponse> sendUser(@RequestBody User user){
        log.info("Send to save user");
        return ResponseEntity.ok(clientService.sendUser(user));
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id){
        log.info("Get user by id {}", id);
        Object obj=clientService.getUserById(id);
        if (obj instanceof User user){
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else if (obj instanceof SaveResponse rs) {
            return new ResponseEntity<>(rs,HttpStatus.OK);
        }return ResponseEntity.status(500).body("Unexpected error");
    }
    @PostMapping("/user/{id}/update")
    public ResponseEntity<SaveResponse> updateUser(@RequestBody User user, @PathVariable Long id){
        log.info("Update user by id {}",id);
        return ResponseEntity.ok(clientService.updateUser(user,id));
    }
    @PostMapping("/user/{id}/delete")
    public ResponseEntity<SaveResponse> deleteUser(@PathVariable Long id){
        log.info("Delete user by id {}",id);
        return ResponseEntity.ok(clientService.deleteUser(id));
    }


    @PostMapping("/order/{id}")
    public ResponseEntity<SaveResponse> sendUserToOrder(@RequestBody User user, @PathVariable Integer id){
        log.info("Send to save user id in order db {}",user.id());
        return ResponseEntity.ok(clientService.sendUserToOrder(user,id));
    }
    @PostMapping("/order")
    public ResponseEntity<SaveResponse> sendOrder(@RequestBody Order order){
        log.info("Save order {}",order);
        return ResponseEntity.ok(clientService.saveOrder(order));
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<Object> getOrder(@PathVariable Integer id){
        log.info("Get order by id {}",id);
        Object obj=clientService.getOrderById(id);
        if (obj instanceof Order order){
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else if (obj instanceof SaveResponse rs) {
            return new ResponseEntity<>(rs,HttpStatus.OK);
        }return ResponseEntity.status(500).body("Unexpected error");
    }
    @PostMapping("/order/{id}/update")
    public ResponseEntity<SaveResponse> updateOrder(@RequestBody Order newOrder,@PathVariable Integer id){
        log.info("Send update order {}",newOrder);
        return ResponseEntity.ok(clientService.updateOrder(newOrder,id));
    }
    @PostMapping("/order/{id}/delete")
    public ResponseEntity<SaveResponse> deleteOrder(@PathVariable Integer id){
        log.info("Delete order by id {}",id);
        return ResponseEntity.ok(clientService.deleteOrder(id));
    }
}
