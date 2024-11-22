package com.example.ExcelReading.exceptionHandling;

import com.example.ExcelReading.commonResponse.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private CommonResponse<Object> commonResponse;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return commonResponse.prepareErrorResponseObject(ex.getMessage(),
                org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
