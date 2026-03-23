package ru.bibikov.servicea.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bibikov.servicea.dto.Order;
import ru.bibikov.servicea.dto.SaveResponse;
import ru.bibikov.servicea.dto.User;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final RestTemplate restTemplate;

    @Value("${server.b-url}")
    private String url;

    public SaveResponse sendUser(User user){
        ResponseEntity<SaveResponse> response=restTemplate.postForEntity(url,user, SaveResponse.class);
        return response.getBody();
    }

    public SaveResponse sendOrder(Order order) {
        ResponseEntity<SaveResponse> responseOrder=restTemplate.postForEntity("http://localhost:8084",order, SaveResponse.class);
        return responseOrder.getBody();
    }
}
