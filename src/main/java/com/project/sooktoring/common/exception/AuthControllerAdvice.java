package com.project.sooktoring.common.exception;

import com.project.sooktoring.common.exception.dto.AuthExResponse;
import com.project.sooktoring.user.auth.exception.ExpiredAccessTokenException;
import com.project.sooktoring.user.auth.exception.ExpiredRefreshTokenException;
import com.project.sooktoring.user.auth.exception.GoogleResourceServerAccessException;
import com.project.sooktoring.user.auth.exception.InvalidGoogleIdTokenException;
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
    public AuthExResponse invalidGoogleIdTokenExceptionHandler(InvalidGoogleIdTokenException e) {
        log.error(e.getMessage());
        return AuthExResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .redirectPath("/auth/login")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AuthExResponse googleResourceServerAccessExceptionHandler(GoogleResourceServerAccessException e) {
        log.error(e.getMessage());
        return AuthExResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .redirectPath("")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AuthExResponse expiredJwtExceptionHandler(ExpiredAccessTokenException e) {
        log.error(e.getMessage());
        return AuthExResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .redirectPath("/auth/refresh")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AuthExResponse expiredJwtExceptionHandler(ExpiredRefreshTokenException e) {
        log.error(e.getMessage());
        return AuthExResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .redirectPath("/auth/login")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AuthExResponse jwtExceptionHandler(JwtException e) {
        log.error(e.getMessage());
        return AuthExResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .redirectPath("/auth/login")
                .build();
    }
}