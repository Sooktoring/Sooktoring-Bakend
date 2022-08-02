package com.project.sooktoring.exception.handler;

import com.project.sooktoring.exception.global.EnumConversionException;
import com.project.sooktoring.exception.handler.dto.AuthExResponse;
import com.project.sooktoring.exception.handler.dto.EnumExResponse;
import com.project.sooktoring.exception.handler.dto.MtrExResponse;
import com.project.sooktoring.exception.domain.mentoring.MtrDuplicateException;
import com.project.sooktoring.exception.domain.mentoring.MtrTargetException;
import com.project.sooktoring.exception.domain.user.auth.ExpiredAccessTokenException;
import com.project.sooktoring.exception.domain.user.auth.ExpiredRefreshTokenException;
import com.project.sooktoring.exception.domain.user.auth.GoogleResourceServerAccessException;
import com.project.sooktoring.exception.domain.user.auth.InvalidGoogleIdTokenException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EnumExResponse enumConversionExceptionHandler(EnumConversionException e) {
        log.error(e.getMessage());
        return EnumExResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .source(e.getSource())
                .build();
    }

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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MtrExResponse mtrDuplicateExceptionHandler(MtrDuplicateException e) {
        log.error(e.getMessage());
        return MtrExResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .request(e.getRequest())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MtrExResponse mtrTargetExceptionHandler(MtrTargetException e) {
        log.error(e.getMessage());
        return MtrExResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .request(e.getRequest())
                .build();
    }
}
