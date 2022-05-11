package com.project.sooktoring.auth.exception;

public class GoogleResourceServerAccessException extends RuntimeException {

    public GoogleResourceServerAccessException() {
        super("Google Resource Server Access Denied");
    }

    public GoogleResourceServerAccessException(String message) {
        super(message);
    }
}
