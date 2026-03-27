package ru.bibikov.order.dto;

import lombok.Builder;

@Builder
public record User (
        Long id,
        String name,
        String email
){
}
