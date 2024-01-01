package com.workintech.s17challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException apiException){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(apiException.getHttpStatus().value(), apiException.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse, apiException.getHttpStatus());

    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception exception){
        ApiErrorResponse apiErrorResponse= new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage() , System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
