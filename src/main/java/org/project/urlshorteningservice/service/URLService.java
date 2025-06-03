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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Random;

@AllArgsConstructor
@Service
public class URLService {

    private final URLRepository urlRepository;

    public ResponseEntity<URLResponse> shortenURL(String longURL) {
        URL url = new URL();

        url.setLongURL(longURL);
        url.setShortURL(generateShortURL());
        url.setCreatedAt(LocalDateTime.now());
        url.setUpdatedAt(LocalDateTime.now());
        url.setAccessCount(0L);

        urlRepository.save(url);
        return ResponseEntity.ok(new URLResponse(url.getLongURL(), url.getShortURL()));
    }

    public ResponseEntity<URLResponse> retrieveLongURL(String shortURL) {
        URL url = urlRepository.findURLByShortURL(shortURL).orElseThrow(() -> new URLNotFoundException("Provided short URL does not exist."));

        url.setAccessCount(url.getAccessCount() + 1);
        url.setUpdatedAt(LocalDateTime.now());
        urlRepository.save(url);
        return ResponseEntity.ok(new URLResponse(url.getLongURL(), url.getShortURL()));
    }

    public ResponseEntity<URLResponse> updateShortURL(String shortURL, String longURL) {
        URL url = urlRepository.findURLByShortURL(shortURL).orElseThrow(() -> new URLNotFoundException("Provided short URL does not exist."));

        if (!url.getLongURL().equals(longURL) && !longURL.isBlank()) {
            url.setLongURL(longURL);
            url.setAccessCount(url.getAccessCount() + 1);
            url.setUpdatedAt(LocalDateTime.now());
            urlRepository.save(url);

            return ResponseEntity.ok(new URLResponse(url.getLongURL(), url.getShortURL()));
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

        return ResponseEntity.ok(new URLStats(url.getLongURL(), url.getShortURL(), url.getAccessCount()));

    }

    private String generateShortURL() {
        int lowerLimit = 48; // integer - 0
        int upperLimit = 122;   // letter - z
        int length = 15;
        Random random = new Random();

        return random.ints(lowerLimit, upperLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(new Random().nextInt(length - 5, length + 5))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public HttpResponse<String> redirect(String shortURL) {
        URL url = urlRepository.findURLByShortURL(shortURL).orElseThrow(() -> new URLNotFoundException("No such short url!"));

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = null;
        HttpResponse<String> response = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://" + url.getLongURL()))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
