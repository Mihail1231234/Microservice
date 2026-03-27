package ru.bibikov.userservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bibikov.userservice.dto.SaveResponse;
import ru.bibikov.userservice.entity.User;
import ru.bibikov.userservice.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void save() throws IllegalAccessException {
        User user= User.builder()
                .id(1L)
                .name("Tom")
                .email("tom@gmail.com")
                .build();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        SaveResponse response=userService.save(user);
        Assertions.assertNotNull(response);
        //Assertions.assertTrue(response.success().);
        Assertions.assertEquals("Tom",user.getName());
        Mockito.verify(userRepository,Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    void findById() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}