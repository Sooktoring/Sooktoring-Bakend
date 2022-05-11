package com.project.sooktoring.auth.exception;

public class InvalidGoogleAccessTokenException extends RuntimeException {

    public InvalidGoogleAccessTokenException() {
        super("Google Access Token is unauthorized");
    }

    public InvalidGoogleAccessTokenException(String message) {
        super(message);
    }
}
