package com.project.sooktoring.common.exception;

import lombok.Getter;
import org.springframework.core.convert.ConversionException;

@Getter
public class EnumConversionException extends ConversionException {

    private final String source;

    public EnumConversionException(String message) {
        super(message);
        source = "";
    }

    public EnumConversionException(String message, Throwable cause) {
        super(message, cause);
        source = "";
    }

    public EnumConversionException(String message, String source) {
        super(message);
        this.source = source;
    }
}
