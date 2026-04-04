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
import ru.bibikov.userservice.util.DataUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void save() throws IllegalAccessException {
        User user = DataUtils.getJohnTransient();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        SaveResponse response = userService.save(user);
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.success());
        Assertions.assertEquals("John", user.getName());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    //???
    @Test
    void findByIdSuccess() {
        User user = DataUtils.getFrankPersisted();
        userRepository.save(user);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        SaveResponse response = userService.findById(user.getId());

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.success());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    void findByIdNotFound() {
        Long id = 1L;

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        SaveResponse response = userService.findById(id);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.success());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    void findUserByIdSuccess() {
        User user = DataUtils.getFrankPersisted();
        userRepository.save(user);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Object userFromDB = userService.findUserById(user.getId());
        User user1=(User) userFromDB;

        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Frank", user1.getName());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    void findUserByIdBad() {
        Long id = 1L;

        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Object result=userService.findById(id);
        SaveResponse rs=(SaveResponse) result;

        Assertions.assertNotNull(rs);
        Assertions.assertFalse(rs.success());
        Assertions.assertEquals(rs.message(),"User not found");
        //Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.any());

    }

    @Test
    void updateUserSuccess() {
        User user = DataUtils.getJohnPersisted();

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(Mockito.any(User.class));

        user.setEmail("jjohan@gmail.com");

        SaveResponse rs = userService.updateUser(user, user.getId());

        Assertions.assertNotNull(rs);
        Assertions.assertTrue(rs.success());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(userRepository, Mockito.times(1)).findById(user.getId());
    }

    @Test
    void updateUserBad() {
        User user = DataUtils.getJohnPersisted();

        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());


        user.setEmail("jjohan@gmail.com");

        SaveResponse rs = userService.updateUser(user, 2L);

        Assertions.assertNotNull(rs);
        Assertions.assertFalse(rs.success());
        Mockito.verify(userRepository, Mockito.times(1)).findById(2L);
    }

    @Test
    void deleteUserSuccess() {
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);

        SaveResponse rs = userService.deleteUser(1L);

        assertNotNull(rs);
        assertTrue(rs.success());
        Mockito.verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUserBad() {
        Mockito.when(userRepository.existsById(2L)).thenReturn(false);

        SaveResponse rs = userService.deleteUser(2L);

        assertNotNull(rs);
        assertFalse(rs.success());
    }
}