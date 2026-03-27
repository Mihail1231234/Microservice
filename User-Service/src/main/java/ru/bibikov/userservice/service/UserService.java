package ru.bibikov.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bibikov.userservice.dto.SaveResponse;
import ru.bibikov.userservice.entity.User;
import ru.bibikov.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SaveResponse save(User user) throws IllegalAccessException {
        validateUser(user);
        try {
            userRepository.save(user);
            return new SaveResponse(true, "ok");
        } catch (Exception e) {
            return new SaveResponse(false, "bad " + e);
        }
    }

    public SaveResponse findById(Long id) {
        validateId(id);
        try {
            userRepository.findById(id).orElseThrow(() -> new RuntimeException("Null"));
            return new SaveResponse(true, "ok");
        } catch (Exception e) {
            return new SaveResponse(false, "bad " + e);
        }
    }

    public User findUserById(Long id) {
        validateId(id);
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Null"));
    }

    @Transactional
    public SaveResponse updateUser(User newUser,Long id){
        validateAll(newUser,id);
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        if (newUser.getName()!=null) user.setName(newUser.getName());
        if (newUser.getEmail()!=null) user.setEmail(newUser.getEmail());
        return new SaveResponse(true,"User has been updated");
    }

    public SaveResponse deleteUser(Long id){
        validateId(id);
        userRepository.deleteById(id);
        return new SaveResponse(true,"User has been deleted");
    }

    private void validateAll(User user,Long id){
        validateId(id);
        validateUserIsNull(user);
    }

    private void validateId(Long id){
        if (id==null) {
            throw new RuntimeException("Id must not be empty");
        }
    }

    private void validateUserIsNull(User user){
        if (user==null){
            throw new RuntimeException("User must not be empty");
        }
    }

    private void validateUser(User user) throws IllegalAccessException {
        if (user.getEmail() == null || user.getEmail().isBlank() || user.getEmail().isEmpty()) {
            throw new IllegalAccessException("Email is not null " + user.getEmail());
        }
    }
}
