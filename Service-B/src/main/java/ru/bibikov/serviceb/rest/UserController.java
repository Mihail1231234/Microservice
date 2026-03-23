package ru.bibikov.serviceb.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bibikov.serviceb.dto.SaveResponse;
import ru.bibikov.serviceb.entity.User;
import ru.bibikov.serviceb.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<SaveResponse> saveEntity(@RequestBody User user) throws IllegalAccessException {
        log.info("save {}", user);
        return ResponseEntity.ok(userService.save(user));
    }
    @GetMapping("/{id}")
    public ResponseEntity<SaveResponse> getUserById(@PathVariable Long id){
        log.info("get by id {}",id);
        return ResponseEntity.ok(userService.findById(id));
    }
}
