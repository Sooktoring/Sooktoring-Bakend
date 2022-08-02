package com.project.sooktoring.user.auth.exception;

public class InvalidGoogleIdTokenException extends RuntimeException {

    public InvalidGoogleIdTokenException() {
        super("Google Id Token is unauthorized");
    }

    public InvalidGoogleIdTokenException(String message) {
        super(message);
    }
}
