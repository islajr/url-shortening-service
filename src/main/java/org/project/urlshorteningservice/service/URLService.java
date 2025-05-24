package org.project.urlshorteningservice.service;

import lombok.AllArgsConstructor;
import org.project.urlshorteningservice.exception.exceptions.DuplicateLongURLException;
import org.project.urlshorteningservice.exception.exceptions.URLNotFoundException;
import org.project.urlshorteningservice.model.URL;
import org.project.urlshorteningservice.model.URLResponse;
import org.project.urlshorteningservice.model.URLStats;
import org.project.urlshorteningservice.repository.URLRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class URLService {

    private final URLRepository urlRepository;

    public ResponseEntity<URLResponse> shortenURL(String longURL) {
        URL url = new URL();

        url.setLongURL(longURL);
        url.setShortURL(generateShortURL(longURL));
        url.setCreatedAt(LocalDateTime.now());
        url.setUpdatedAt(LocalDateTime.now());

        urlRepository.save(url);
        return ResponseEntity.ok(new URLResponse(url.getLongURL(), url.getShortURL()));
    }

    public ResponseEntity<URLResponse> retrieveLongURL(String shortURL) {
        URL url = urlRepository.findURLByShortURL(shortURL).orElseThrow(() -> new URLNotFoundException("Provided short URL does not exist."));

        url.setAccessCount(url.getAccessCount() + 1);
        urlRepository.save(url);
        return ResponseEntity.ok(new URLResponse(url.getShortURL(), url.getLongURL()));
    }

    public ResponseEntity<URLResponse> updateShortURL(String shortURL, String longURL) {
        URL url = urlRepository.findURLByShortURL(shortURL).orElseThrow(() -> new URLNotFoundException("Provided short URL does not exist."));

        if (!url.getLongURL().equals(longURL) && !longURL.isBlank()) {
            url.setLongURL(longURL);
            url.setUpdatedAt(LocalDateTime.now());
            urlRepository.save(url);

            return ResponseEntity.ok(new URLResponse(url.getShortURL(), url.getLongURL()));
        }

        throw new DuplicateLongURLException("Provided long URL hasn't changed.");
    }

    public ResponseEntity<String> deleteShortURL(String shortURL) {

        URL url = urlRepository.findURLByShortURL(shortURL).orElseThrow(() -> new URLNotFoundException("Provided short URL does not exist."));

        urlRepository.delete(url);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted URL");
    }

    public ResponseEntity<URLStats> getURLStats(String shortURL) {
        URL url = urlRepository.findURLByShortURL(shortURL).orElseThrow(() -> new URLNotFoundException("Provided short URL does not exist."));

        return ResponseEntity.ok(new URLStats(url.getShortURL(), url.getLongURL(), url.getAccessCount()));

    }

    private String generateShortURL(String longURL) {

        return UUID.fromString(longURL).toString();
    }
}
