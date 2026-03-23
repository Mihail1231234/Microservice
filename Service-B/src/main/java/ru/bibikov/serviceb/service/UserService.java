package ru.bibikov.serviceb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bibikov.serviceb.dto.SaveResponse;
import ru.bibikov.serviceb.entity.User;
import ru.bibikov.serviceb.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SaveResponse save(User user) throws IllegalAccessException {
        if (user.getEmail()==null||user.getEmail().isBlank()||user.getEmail().isEmpty()){
            throw new IllegalAccessException("Email is not null "+ user.getEmail());
        }
        try {
            userRepository.save(user);
            return new SaveResponse(true,"ok");
        }catch (Exception e) {
            return new SaveResponse(false,"bad "+ e);
        }
    }

    public SaveResponse findById(Long id){
        try {
            userRepository.findById(id).orElseThrow(()->new RuntimeException("Null"));
            return new SaveResponse(true,"ok");
        }catch (Exception e){
            return new SaveResponse(false,"bad "+e);
        }
    }
}
