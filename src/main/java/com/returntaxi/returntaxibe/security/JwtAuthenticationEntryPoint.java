package com.returntaxi.returntaxibe.security;

import com.returntaxi.returntaxibe.advice.entity.ErrorResponseTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponseTemplate<String> errorResponse = ErrorResponseTemplate.<String>builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Full authentication is required to access this resource")
                .exception(authException.getClass().getName())
                .path(request.getServletPath())
                .isExpired(false)
                .build();

        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }
}
