package ru.bibikov.servicea.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bibikov.servicea.dto.Order;
import ru.bibikov.servicea.dto.SaveResponse;
import ru.bibikov.servicea.dto.User;
import ru.bibikov.servicea.dto.UserAndOrder;
import ru.bibikov.servicea.service.ClientService;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class PackageController {

    private final ClientService clientService;

    @PostMapping()
    public ResponseEntity<SaveResponse> sendUser(@RequestBody User user){
        return ResponseEntity.ok(clientService.sendUser(user));
    }

    @PostMapping("/order")
    public ResponseEntity<SaveResponse> sendOrder(@RequestBody Order order){
        /*User user=userAndOrder.user();
        Order order=userAndOrder.order();*/
        return ResponseEntity.ok(clientService.sendOrder(order));
    }
}
