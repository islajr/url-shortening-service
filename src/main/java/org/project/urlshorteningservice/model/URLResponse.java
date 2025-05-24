package org.project.urlshorteningservice.model;

public record URLResponse(
        String longURL,
        String shortURL
) {
}
