package com.returntaxi.returntaxibe.advice;

import com.returntaxi.returntaxibe.advice.entity.ErrorResponseTemplate;
import com.returntaxi.returntaxibe.advice.exception.DataNotFoundException;
import com.returntaxi.returntaxibe.advice.exception.UnauthorizedAccessException;
import com.returntaxi.returntaxibe.advice.exception.OperationFailedException;
import com.returntaxi.returntaxibe.advice.exception.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.connector.ClientAbortException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static ErrorResponseTemplate<String> buildError(String exception, HttpServletRequest request, String message,
                                                            HttpStatus httpStatus, Boolean isExpired) {
        return ErrorResponseTemplate.<String>builder()
                .exception(exception)
                .status(httpStatus.value())
                .path(request.getServletPath())
                .message(message)
                .isExpired(isExpired)
                .build();
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponseTemplate<String>> handleDataNotFoundException(
            DataNotFoundException dataNotFoundException, HttpServletRequest request) {
        String message = dataNotFoundException.getMessage();
        ErrorResponseTemplate<String> errorResponse = buildError(dataNotFoundException.getClass().getName(), request,
                message,
                HttpStatus.BAD_REQUEST, false);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponseTemplate<String>> handleUnauthorizedAccessException(
            UnauthorizedAccessException unauthorizedAccessException, HttpServletRequest request) {
        String message = unauthorizedAccessException.getMessage();
        ErrorResponseTemplate<String> errorResponse = buildError(unauthorizedAccessException.getClass().getName(),
                request,
                message, HttpStatus.FORBIDDEN, false);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OperationFailedException.class)
    public ResponseEntity<ErrorResponseTemplate<String>> handleOperationFailedException(
            OperationFailedException operationFailedException, HttpServletRequest request) {
        String message = operationFailedException.getMessage();
        ErrorResponseTemplate<String> errorResponse = buildError(operationFailedException.getClass().getName(), request,
                message, HttpStatus.INTERNAL_SERVER_ERROR, false);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseTemplate<String>> handleUserAlreadyExistsException(
            UserAlreadyExistsException userAlreadyExistsException, HttpServletRequest request) {
        String message = userAlreadyExistsException.getMessage();
        ErrorResponseTemplate<String> errorResponse = buildError(userAlreadyExistsException.getClass().getName(),
                request,
                message, HttpStatus.CONFLICT, false);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseTemplate<String>> handleUsernameNotFoundException(
            UsernameNotFoundException usernameNotFoundException, HttpServletRequest request) {
        String message = usernameNotFoundException.getMessage();
        ErrorResponseTemplate<String> errorResponse = buildError(usernameNotFoundException.getClass().getName(),
                request,
                message, HttpStatus.NOT_FOUND, false);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseTemplate<String>> handleBadCredentialsException(
            BadCredentialsException badCredentialsException, HttpServletRequest request) {
        String message = "Invalid username or password";
        ErrorResponseTemplate<String> errorResponse = buildError(badCredentialsException.getClass().getName(), request,
                message,
                HttpStatus.UNAUTHORIZED, false);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ClientAbortException.class)
    public ResponseEntity<Void> handleClientAbortException(ClientAbortException ex) {
        log.debug("Client disconnected before response was fully written: {}", ex.getMessage());
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseTemplate<String>> handleGenericException(Exception exception,
                                                                                HttpServletRequest request) {
        log.error("Unhandled exception: ", exception);
        String message = "An internal server error occurred";
        ErrorResponseTemplate<String> errorResponse = buildError(exception.getClass().getName(), request, message,
                HttpStatus.INTERNAL_SERVER_ERROR, false);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
