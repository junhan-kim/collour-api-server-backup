package com.collour.api.config;

import com.collour.api.dto.ExceptionDTO;
import com.collour.api.dto.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle exception for 'Exception.class' from all controller.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleException(Exception exception) {
        if (exception instanceof ExceptionDTO) {
            ExceptionDTO exceptionDTO = (ExceptionDTO) exception;
            return new ResponseEntity<>(new ExceptionResponseDTO(exceptionDTO.getErrorCode().getCode(), exceptionDTO.getErrorCode().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(new ExceptionResponseDTO("COMMON_1", exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // example code block : exception handler for user exception.
    // @ExceptionHandler(UserException.class)
    // public ResponseEntity<ExceptionResponseDTO> handleException(UserException exception){
}
