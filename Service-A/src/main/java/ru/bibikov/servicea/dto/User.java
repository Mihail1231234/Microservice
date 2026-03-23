package ru.bibikov.servicea.dto;

import lombok.Builder;

@Builder
public record User (
    String name,
    String email
){}
