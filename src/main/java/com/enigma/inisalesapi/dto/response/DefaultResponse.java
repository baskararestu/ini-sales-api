package com.enigma.inisalesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DefaultResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
}
