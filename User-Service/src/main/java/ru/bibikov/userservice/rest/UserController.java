package ru.bibikov.userservice.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bibikov.userservice.dto.SaveResponse;
import ru.bibikov.userservice.entity.User;
import ru.bibikov.userservice.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<SaveResponse> getUserById(@PathVariable Long id) {
        log.info("get by id {}", id);
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/us/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        log.info("get by id {}", id);
        Object result = userService.findUserById(id);
        if (result instanceof User user) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else if (result instanceof SaveResponse rs) {
            return new ResponseEntity<>(rs,HttpStatus.NOT_FOUND);
        }return ResponseEntity.status(500).body("Unexpected error");
    }

    @PostMapping
    public ResponseEntity<SaveResponse> saveEntity(@RequestBody User user) throws IllegalAccessException {
        log.info("save {}", user);
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<SaveResponse> updateUser(@RequestBody User user, @PathVariable Long id) {
        log.info("Update user {}", user);
        return ResponseEntity.ok(userService.updateUser(user, id));
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<SaveResponse> deleteUser(@PathVariable Long id) {
        log.info("Delete user by id {}", id);
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}

