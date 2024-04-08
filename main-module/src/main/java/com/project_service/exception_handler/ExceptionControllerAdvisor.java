package com.project_service.exception_handler;

import com.project_service.dto.ErrorData;
import com.project_service.exception.*;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Profile("!debug")
@RestControllerAdvice
public class ExceptionControllerAdvisor {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorData> handleRuntimeException(RuntimeException exception) {
        ErrorData errorData = new ErrorData(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(),
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            CurrencyNotFoundException.class,
            BookingNotFoundException.class,
            ClientNotFoundException.class,
            ApartmentNotFoundException.class,
            ApartmentCategoryNotFoundException.class,
            PriceCategoryNotFoundException.class,
            PriceNotFoundException.class
    })
    public ResponseEntity<ErrorData> handleEntityNotFoundException(RuntimeException exception) {
        ErrorData errorData = new ErrorData(HttpStatus.NOT_FOUND, LocalDateTime.now(),
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NotOwnerException.class, AuthenticationException.class})
    public ResponseEntity<ErrorData> handleForbiddenException(AccessDeniedException exception) {
        ErrorData errorData = new ErrorData(HttpStatus.FORBIDDEN, LocalDateTime.now(),
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorData, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorData> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        ErrorData errorData = new ErrorData(HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorData> handleArgumentNotValid(MethodArgumentNotValidException exception) {
        String message = getMessage(exception);
        ErrorData errorModel = new ErrorData(HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                message, exception.toString());
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    private String getMessage(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }
}
