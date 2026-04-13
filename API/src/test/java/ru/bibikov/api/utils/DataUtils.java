package ru.bibikov.api.utils;

import ru.bibikov.api.dto.Order;
import ru.bibikov.api.dto.User;

public class DataUtils {
    public static Order getIphoneTransient(){
        return Order.builder()
                .userId(1L)
                .unitPrice(500)
                .quantityOrder(1)
                .orderName("Iphone")
                .quantityHave(15)
                .build();
    }
    public static Order getIphonePersistedWithoutUserId(){
        return Order.builder()
                .id(1)
                .unitPrice(500)
                .quantityOrder(1)
                .orderName("Iphone")
                .quantityHave(15)
                .build();
    }
    public static Order getIphonePersisted(){
        return Order.builder()
                .id(1)
                .userId(1L)
                .unitPrice(500)
                .quantityOrder(1)
                .orderName("Iphone")
                .quantityHave(15)
                .build();
    }
    public static User getJohnTransient(){
        return User.builder()
                .name("John")
                .email("john@gmail.com")
                .build();
    }
    public static User getMikeTransient(){
        return User.builder()
                .name("Mike")
                .email("mike@gmail.com")
                .build();
    }
    public static User getFrankTransient(){
        return User.builder()
                .name("Frank")
                .email("frank@gmail.com")
                .build();
    }
    public static User getMikePersisted(){
        return User.builder()
                .id(1L)
                .name("Mike")
                .email("mike@gmail.com")
                .build();
    }
    public static User getFrankPersisted(){
        return User.builder()
                .id(1L)
                .name("Frank")
                .email("frank@gmail.com")
                .build();
    }
    public static User getJohnPersisted(){
        return User.builder()
                .id(1L)
                .name("John")
                .email("john@gmail.com")
                .build();
    }
}
