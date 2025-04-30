package com.example.GenericLogicService.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ResponseUtil {
    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    private static final String STATUS_KEY = "status";
    private static final String MESSAGE_KEY = "message";
    private static final String RESULT_KEY = "result";

    private ResponseUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Success Response with List Result
    public static <T> ResponseEntity<Map<String, Object>> successResponse(String message, List<T> result) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, HttpStatus.OK.value());
        response.put(MESSAGE_KEY, message);
        response.put(RESULT_KEY, result);
        logger.info("{} with result: {}", message, result);
        return ResponseEntity.ok(response);  // Return ResponseEntity with 200 OK
    }

    // Success Response for Insert (without result)
    public static ResponseEntity<Map<String, Object>> successResponseInsert(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, HttpStatus.OK.value());
        response.put(MESSAGE_KEY, message);
        return ResponseEntity.ok(response);  // Return ResponseEntity with 200 OK
    }

    // Success Response for Insert (with result)
    public static ResponseEntity<Map<String, Object>> successResponseInsert(String message, Object object) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, HttpStatus.OK.value());
        response.put(MESSAGE_KEY, message);
        response.put(RESULT_KEY, object);
        return ResponseEntity.ok(response);  // Return ResponseEntity with 200 OK
    }

    // Success Response for Update
    public static ResponseEntity<Map<String, Object>> successResponseUpdate(String message, Object result) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, HttpStatus.OK.value());
        response.put(MESSAGE_KEY, message);
        response.put(RESULT_KEY, result);
        logger.info("{} with result: {}", message, result);
        return ResponseEntity.ok(response);  // Return ResponseEntity with 200 OK
    }

    // No Content Response
    public static ResponseEntity<Map<String, Object>> noContentResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, HttpStatus.NO_CONTENT.value());
        response.put(MESSAGE_KEY, message);
        logger.warn(message);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);  // Return ResponseEntity with 204 No Content
    }

    // Error Response
    public static ResponseEntity<Map<String, Object>> errorResponse(String message, Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put(MESSAGE_KEY, message);
        logger.error(message, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // Return ResponseEntity with 500 Internal Server Error
    }

    public static ResponseEntity<Map<String, Object>> successDeleteResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, HttpStatus.OK.value());  // 200 OK status
        response.put(MESSAGE_KEY, message);
        logger.info(message);  // Log the success message
        return ResponseEntity.ok(response);  // Return ResponseEntity with 200 OK status
    }

}
