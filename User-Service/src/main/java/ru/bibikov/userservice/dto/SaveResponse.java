package ru.bibikov.userservice.dto;

import lombok.Builder;

@Builder
public record SaveResponse(
        boolean success,
        String message
) {
}
