package ru.bibikov.api.dto;

import lombok.Builder;

@Builder
public record SaveResponse(
        boolean success,
        String message
) {

}
