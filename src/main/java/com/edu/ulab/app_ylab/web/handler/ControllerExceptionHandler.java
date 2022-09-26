package com.edu.ulab.app_ylab.web.handler;

import com.edu.ulab.app_ylab.exception.NoSuchBookException;
import com.edu.ulab.app_ylab.exception.NoSuchUserException;
import com.edu.ulab.app_ylab.exception.NotFoundException;
import com.edu.ulab.app_ylab.web.response.BaseWebResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Set;


@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseWebResponse> handleNotFoundExceptionException(@NonNull final NotFoundException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(NoSuchBookException.class)
    public ResponseEntity<BaseWebResponse> handleNotFoundExceptionException(@NonNull final NoSuchBookException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<BaseWebResponse> handleAlreadyExistExceptionException(@NonNull final NoSuchUserException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseWebResponse> handleConstraintViolationException(@NonNull final ConstraintViolationException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createConstraintViolationErrorMessage(exc)));
    }


    private String createErrorMessage(Exception exception) {
        final String message = exception.getMessage();
        log.error(ExceptionHandlerUtils.buildErrorMessage(exception));
        return message;
    }

    private String createConstraintViolationErrorMessage(ConstraintViolationException exception) {
        final Set<ConstraintViolation<?>> message = exception.getConstraintViolations();
        log.error(ExceptionHandlerUtils.buildErrorMessage(exception));
        return message.stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse(exception.getMessage());

    }
}
