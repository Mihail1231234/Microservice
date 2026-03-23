package ru.bibikov.servicea.dto;

import lombok.Builder;

@Builder
public record UserAndOrder (
        User user,
        Order order
) {
}
