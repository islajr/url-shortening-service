package org.project.urlshorteningservice.exception.exceptions;

public class DuplicateLongURLException extends RuntimeException {

    String message;

    public DuplicateLongURLException(String message) {
        super(message);
        this.message = message;
    }
}
