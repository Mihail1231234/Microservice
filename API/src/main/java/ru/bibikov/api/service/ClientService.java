package ru.bibikov.api.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bibikov.api.dto.Order;
import ru.bibikov.api.dto.SaveResponse;
import ru.bibikov.api.dto.User;



@Service
@RequiredArgsConstructor
public class ClientService {
    private final RestTemplate restTemplate;

    @Value("${server.urlUser}")
    private String urlUser;
    @Value("${server.urlOrder}")
    private String urlOrder;

    public SaveResponse sendUser(User user){

        ResponseEntity<SaveResponse> response=restTemplate.postForEntity(urlUser,user, SaveResponse.class);
        return response.getBody();
    }

    public User getUserById(Long id) {
        ResponseEntity<User> response=restTemplate.getForEntity(urlUser+"/us/"+id, User.class);
        return response.getBody();
    }

    public SaveResponse updateUser(User user, Long id) {
        ResponseEntity<SaveResponse> response=restTemplate.postForEntity(urlUser+"/"+id+"/update",user,SaveResponse.class);
        return response.getBody();
    }

    public SaveResponse deleteUser(Long id) {
        ResponseEntity<SaveResponse> response=restTemplate.postForEntity(urlUser+"/"+id+"/delete",null,SaveResponse.class);
        return response.getBody();
    }



    public SaveResponse sendUserToOrder(User user, Integer id){
        ResponseEntity<User> responseUser=restTemplate.getForEntity(urlUser+"/"+user.id(), User.class);
        if (responseUser==null) throw new RuntimeException("User not found");
        ResponseEntity<SaveResponse> response=restTemplate.postForEntity(urlOrder+"/order-userId/"+id,user,SaveResponse.class);
        return response.getBody();
    }

    public SaveResponse saveOrder(Order order) {
        ResponseEntity<SaveResponse> responseOrder=restTemplate
                .postForEntity(urlOrder+"/save",order, SaveResponse.class);
        return responseOrder.getBody();
    }

    public Order getOrderById(Integer id) {
        ResponseEntity<Order> response=restTemplate.getForEntity(urlOrder+"/"+id, Order.class);
        return response.getBody();
    }

    public SaveResponse updateOrder(Order newOrder, Integer id) {
        ResponseEntity<SaveResponse> response=restTemplate.postForEntity(urlOrder+"/"+id+"/update",newOrder, SaveResponse.class);
        return response.getBody();
    }

    public SaveResponse deleteOrder(Integer id) {
        ResponseEntity<SaveResponse> response=restTemplate.postForEntity(urlOrder+"/"+id+"/delete", null, SaveResponse.class);
        return response.getBody();
    }
}
