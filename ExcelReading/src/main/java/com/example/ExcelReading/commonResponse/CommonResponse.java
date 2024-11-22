package com.example.ExcelReading.commonResponse;

import com.example.ExcelReading.responseEntity.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

@ControllerAdvice
@Component
public class CommonResponse<T> {

    public ResponseEntity<ResponseModel<T>> prepareSuccessResponseObject(T result, HttpStatus status) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setStatusCode(status.value());
        response.setStatus("SUCCESS");
        if (result instanceof List<?>) {
            response.setResult((List<T>) result);
        } else {
            response.setResult(List.of(result));
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    public ResponseEntity<ResponseModel<T>> prepareErrorResponseObject(String message, HttpStatus status) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setStatus("FAILED");
        response.setStatusCode(status.value());
        response.setError(message);
        response.setResult(List.of((T) message));
        return new ResponseEntity<>(response, status);
    }


    public ResponseEntity<ResponseModel<T>> prepareFailedResponse(String error) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setStatus("FAILURE");
        response.setError(error);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}