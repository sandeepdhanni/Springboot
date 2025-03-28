package com.ml_service.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

@ControllerAdvice
@Component
public class CommonResponse<T> {
    public ResponseEntity<Response<T>> prepareSuccessResponseObject(T result, HttpStatus status) {
        Response<T> response = new Response<>();
        response.setStatuscode(status.value());
        response.setStatus("SUCCESS");
        if (result instanceof List<?>) {
            response.setResult((List<T>) result);
        } else {
            response.setResult(List.of(result));
        }
        return new ResponseEntity<>(response,status);
    }


    public ResponseEntity<Response<T>> prepareErrorResponseObject(String message, HttpStatus status) {
        Response<T> response = new Response<>();
        response.setStatus("Error");
        response.setStatuscode(status.value());
        response.setError(message);
        response.setResult(List.of((T) message));
        return new ResponseEntity<>(response, status);
    }


    public ResponseEntity<Response<T>> prepareFailedResponse(String error,HttpStatus status) {
        Response<T> response = new Response<>();
        response.setStatuscode(status.value());
        response.setStatus("FAILURE");
        response.setError(error);
        return new ResponseEntity<>(response,status);
    }

}
