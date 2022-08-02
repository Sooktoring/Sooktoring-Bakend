package com.project.sooktoring.user.auth.exception;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;

public class ExpiredAccessTokenException extends ExpiredJwtException {

    public ExpiredAccessTokenException(Header header, Claims claims, String message) {
        super(header, claims, message);
    }

    public ExpiredAccessTokenException(Header header, Claims claims, String message, Throwable cause) {
        super(header, claims, message, cause);
    }
}
