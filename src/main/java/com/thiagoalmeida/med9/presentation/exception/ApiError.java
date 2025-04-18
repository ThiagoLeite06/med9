package com.thiagoalmeida.med9.presentation.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
    String path,
    String message,
    int statusCode,
    LocalDateTime timestamp,
    List<ValidationError> errors
) {
    @Builder
    public record ValidationError(
        String field,
        String message
    ) {}
}
