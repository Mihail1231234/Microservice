package ru.bibikov.api.dto;

import lombok.Builder;

@Builder
public record UserAndOrder (
        User user,
        Order order
) {
}
