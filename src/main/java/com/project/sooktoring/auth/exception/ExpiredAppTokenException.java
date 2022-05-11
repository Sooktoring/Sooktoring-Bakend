package com.project.sooktoring.auth.exception;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;

public class ExpiredAppTokenException extends ExpiredJwtException {

    public ExpiredAppTokenException(Header header, Claims claims, String message) {
        super(header, claims, message);
    }

    public ExpiredAppTokenException(Header header, Claims claims, String message, Throwable cause) {
        super(header, claims, message, cause);
    }
}
