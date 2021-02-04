package com.scb.bank.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@ControllerAdvice
public class BankAppCustomExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        AtomicReference<String> errorMsg= new AtomicReference<>();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMsg.set(errorMessage);
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errorMsg.get(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionFailureException.class)
    public ResponseEntity<Object> transactionFailureCustomException(TransactionFailureException ex) {
        return new ResponseEntity<>("Transaction failed", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateRangeException.class)
    public ResponseEntity<Object> dateRangeCustomException(DateRangeException ex) {
        return new ResponseEntity<>("Date should not be in future", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateFormatException.class)
    public ResponseEntity<Object> dateRangeCustomException(DateFormatException ex) {
        return new ResponseEntity<>("Date format should be yyyy-MM-dd", HttpStatus.BAD_REQUEST);
    }
}
