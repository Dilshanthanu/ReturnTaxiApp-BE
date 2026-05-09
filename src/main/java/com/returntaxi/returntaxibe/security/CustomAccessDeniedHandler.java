package com.returntaxi.returntaxibe.security;

import com.returntaxi.returntaxibe.advice.entity.ErrorResponseTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ErrorResponseTemplate<String> errorResponse = ErrorResponseTemplate.<String>builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message("Access Denied: You do not have permission to access this resource")
                .exception(accessDeniedException.getClass().getName())
                .path(request.getServletPath())
                .isExpired(false)
                .build();

        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }
}
