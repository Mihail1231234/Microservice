package ru.bibikov.servicea.dto;

import lombok.Builder;

@Builder
public record SaveResponse(
        boolean success,
        String message
) {

}
