package com.test.registration.exception;

import com.test.registration.controller.UserController;
import com.test.registration.data.ResponseMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableWebMvc
@RestControllerAdvice
public class CustomExceptionHandler {

    Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler({UsernameAlreadyExistsException.class, MissingRequiredFieldException.class, UserNotFoundException.class,
            UserNotFoundException.class, UserAlreadyDeletedException.class})
    public ResponseEntity<ResponseMessage> handleBadRequests(Exception exception) {
        logger.error("Error encountered", exception);
        return new ResponseEntity<>(new ResponseMessage("Invalid Request",new String[]{exception.getMessage()}), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseMessage> handleBadRequests(ConstraintViolationException exception) {
        logger.error("Error encountered", exception);
        String[] errors = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessageTemplate)
                .toArray(String[]::new);
        return new ResponseEntity<>(new ResponseMessage("Invalid Request",errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        logger.error("Error encountered", exception);
        Map<String, List<String>> body = new HashMap<>();

        String[] errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toArray(String[]::new);

        return new ResponseEntity<>(new ResponseMessage("Invalid Request",errors), HttpStatus.BAD_REQUEST);
    }

}