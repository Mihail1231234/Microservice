package ru.bibikov.order.util;

import ru.bibikov.order.entity.Order;

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
}
