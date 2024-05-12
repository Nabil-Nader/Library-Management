package com.library.management.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.management.dto.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {


    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFoundxception(BookNotFoundException ex, HttpServletRequest request) {
        log.error(ex.getMessage());

        return new ResponseEntity<>(new Response<>(request.getRequestURI(), ex.getMessage(), false, HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
    


}
