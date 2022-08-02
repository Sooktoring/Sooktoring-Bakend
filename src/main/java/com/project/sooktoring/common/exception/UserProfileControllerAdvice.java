package com.project.sooktoring.common.exception;

import com.project.sooktoring.user.profile.controller.UserProfileController;
import com.project.sooktoring.common.exception.dto.EnumExResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = UserProfileController.class)
public class UserProfileControllerAdvice {

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
}
