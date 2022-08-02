package com.project.sooktoring.exception.domain.user.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;

public class ExpiredRefreshTokenException extends ExpiredJwtException {

    public ExpiredRefreshTokenException(Header header, Claims claims, String message) {
        super(header, claims, message);
    }

    public ExpiredRefreshTokenException(String message) {
        super(null, null, message);
    }
}
