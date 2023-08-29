package com.tomlegodais.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiExceptionDto {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant timestamp;
    private String path;
    private String message;
}
