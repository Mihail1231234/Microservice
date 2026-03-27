package ru.bibikov.api.dto;

import lombok.Builder;

@Builder
public record User (
        Long id,
        String name,
        String email
){}
