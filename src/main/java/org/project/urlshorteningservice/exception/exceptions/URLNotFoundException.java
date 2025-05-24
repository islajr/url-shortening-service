package org.project.urlshorteningservice.exception.exceptions;

public class URLNotFoundException extends RuntimeException {

    String message;

    public URLNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
