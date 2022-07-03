package com.project.sooktoring.exception.exhandler;

import com.project.sooktoring.controller.MentoringController;
import com.project.sooktoring.dto.response.EnumExResponse;
import com.project.sooktoring.dto.response.MtrExResponse;
import com.project.sooktoring.exception.EnumConversionException;
import com.project.sooktoring.exception.MtrDuplicateException;
import com.project.sooktoring.exception.MtrTargetException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = MentoringController.class)
public class MentoringControllerAdvice {

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
