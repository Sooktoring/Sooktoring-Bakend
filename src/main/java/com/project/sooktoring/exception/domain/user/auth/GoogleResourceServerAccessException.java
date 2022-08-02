package com.project.sooktoring.exception.domain.user.auth;

public class GoogleResourceServerAccessException extends RuntimeException {

    public GoogleResourceServerAccessException() {
        super("Google Resource Server Access Denied");
    }

    public GoogleResourceServerAccessException(String message) {
        super(message);
    }
}
