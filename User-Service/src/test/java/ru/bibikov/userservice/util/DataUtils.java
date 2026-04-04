package ru.bibikov.userservice.util;

import ru.bibikov.userservice.entity.User;

public class DataUtils {

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
