package com.project.sooktoring.user.auth.exception;

public class GoogleResourceServerAccessException extends RuntimeException {

    public GoogleResourceServerAccessException() {
        super("Google Resource Server Access Denied");
    }

    public GoogleResourceServerAccessException(String message) {
        super(message);
    }
}
