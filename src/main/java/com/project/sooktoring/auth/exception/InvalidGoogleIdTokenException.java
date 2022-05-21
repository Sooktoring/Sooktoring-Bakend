package com.project.sooktoring.auth.exception;

public class InvalidGoogleIdTokenException extends RuntimeException {

    public InvalidGoogleIdTokenException() {
        super("Google Id Token is unauthorized");
    }

    public InvalidGoogleIdTokenException(String message) {
        super(message);
    }
}
