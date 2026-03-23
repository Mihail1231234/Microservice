package ru.bibikov.order.dto;

import lombok.Builder;

@Builder
public record User (
        Integer id,
        String name,
        String email
){
}
