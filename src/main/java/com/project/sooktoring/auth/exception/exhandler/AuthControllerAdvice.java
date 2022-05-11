package com.project.sooktoring.auth.exception.exhandler;

import com.project.sooktoring.auth.dto.AuthExResponse;
import com.project.sooktoring.auth.exception.ExpiredAppTokenException;
import com.project.sooktoring.auth.exception.ExpiredRefreshTokenException;
import com.project.sooktoring.auth.exception.GoogleResourceServerAccessException;
import com.project.sooktoring.auth.exception.InvalidGoogleAccessTokenException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.project.sooktoring.auth")
public class AuthControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AuthExResponse invalidGoogleAccessTokenExceptionHandler(InvalidGoogleAccessTokenException e) {
        log.error(e.getMessage());
        return AuthExResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .redirectUri("/auth/login")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AuthExResponse googleResourceServerAccessExceptionHandler(GoogleResourceServerAccessException e) {
        log.error(e.getMessage());
        return AuthExResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .redirectUri("")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AuthExResponse expiredJwtExceptionHandler(ExpiredAppTokenException e) {
        log.error(e.getMessage());
        return AuthExResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .redirectUri("/auth/refresh")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AuthExResponse expiredJwtExceptionHandler(ExpiredRefreshTokenException e) {
        log.error(e.getMessage());
        return AuthExResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .redirectUri("/auth/login")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AuthExResponse jwtExceptionHandler(JwtException e) {
        log.error(e.getMessage());
        return AuthExResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .redirectUri("/auth/login")
                .build();
    }
}