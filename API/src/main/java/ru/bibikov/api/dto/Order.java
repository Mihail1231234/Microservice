package ru.bibikov.api.dto;

import lombok.Builder;


@Builder
public record Order (
    Integer id,
    Long userId,
    Integer unitPrice,
    Integer quantityOrder,
    String orderName,
    Integer quantityHave
)
{}

