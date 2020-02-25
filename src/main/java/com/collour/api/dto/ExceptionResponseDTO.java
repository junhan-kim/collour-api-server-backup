package com.collour.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class ExceptionResponseDTO {

    private String code;
    private String message;
}
