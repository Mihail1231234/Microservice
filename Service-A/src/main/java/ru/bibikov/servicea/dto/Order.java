package ru.bibikov.servicea.dto;

import lombok.Builder;


@Builder
public record Order (
    Integer id,
    Integer userId,
    Integer unitPrice,
    Integer quantityOrder,
    String orderName,
    Integer quantityHave
)
{}

