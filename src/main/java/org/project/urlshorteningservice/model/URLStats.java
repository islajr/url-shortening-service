package org.project.urlshorteningservice.model;

public record URLStats (
        String shortURL,
        String longURL,
        Long accessCount
) {

}
