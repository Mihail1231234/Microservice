package ru.bibikov.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bibikov.userservice.dto.SaveResponse;
import ru.bibikov.userservice.entity.User;
import ru.bibikov.userservice.exception.UserNotFoundException;
import ru.bibikov.userservice.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ResourcePatternResolver resourcePatternResolver;

    public SaveResponse save(User user) throws IllegalAccessException {
        validateUser(user);
        try {
            userRepository.save(user);
            return new SaveResponse(true, "ok");
        } catch (RuntimeException e) {
            return new SaveResponse(false, "bad " + e);
        }
    }

    public SaveResponse findById(Long id) {
        validateId(id);
        try {
            userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
            return new SaveResponse(true, "ok");
        } catch (UserNotFoundException e) {
            return new SaveResponse(false, "User not found");
        }
    }

    public Object findUserById(Long id) {
        validateId(id);
        try {
            return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        } catch (UserNotFoundException e){
            return new SaveResponse(false,"User not found");
        }
    }

    public SaveResponse updateUser(User newUser, Long id) {
        validateAll(newUser, id);
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
            updateUserCode(newUser, user);
            userRepository.save(user);
            return new SaveResponse(true, "User has been updated");
        } catch (UserNotFoundException e) {
            return new SaveResponse(false, "User is not found");
        }
    }


    public SaveResponse deleteUser(Long id) {
        validateId(id);
        if (!userRepository.existsById(id)) {
            return new SaveResponse(false, "User not found");
        }
        userRepository.deleteById(id);
        return new SaveResponse(true, "User has been deleted");
    }
    /*public SaveResponse deleteUser(Long id) {
        validateId(id);
        if (!userRepository.existsById(id)){
            return new SaveResponse(false,"User not found");
        }
        userRepository.deleteById(id);
        return new SaveResponse(true, "User has been deleted");
    }*/

    private void validateAll(User user, Long id) {
        validateId(id);
        validateUserIsNull(user);
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new RuntimeException("Id must not be empty");
        }
    }

    private void validateUserIsNull(User user) {
        if (user == null) {
            throw new RuntimeException("User must not be empty");
        }
    }

    private void validateUser(User user) throws IllegalAccessException {
        if (user.getEmail() == null || user.getEmail().isBlank() || user.getEmail().isEmpty()) {
            throw new IllegalAccessException("Email is not null " + user.getEmail());
        }
    }

    private void updateUserCode(User newUser, User user) {
        if (newUser.getName() != null) user.setName(newUser.getName());
        if (newUser.getEmail() != null) user.setEmail(newUser.getEmail());
    }
}
