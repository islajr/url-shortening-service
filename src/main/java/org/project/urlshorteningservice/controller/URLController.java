package org.project.urlshorteningservice.controller;

import lombok.AllArgsConstructor;
import org.project.urlshorteningservice.model.URLResponse;
import org.project.urlshorteningservice.model.URLStats;
import org.project.urlshorteningservice.service.URLService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url-shortener")
@AllArgsConstructor
public class URLController {

    private final URLService urlService;

    @PostMapping("/")
    public ResponseEntity<URLResponse> createURL(@RequestBody String longURL) {
        return urlService.shortenURL(longURL);
    }

    @GetMapping("/")
    public ResponseEntity<URLResponse> retrieveLongURL(@RequestParam String shortURL) {
        return urlService.retrieveLongURL(shortURL);
    }

    @PutMapping("/")
    public ResponseEntity<URLResponse> updateShortURL(@RequestParam String shortURL, @RequestBody String longURL) {
        return urlService.updateShortURL(shortURL, longURL);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteShortURL(@RequestParam String shortURL) {
        return urlService.deleteShortURL(shortURL);
    }

    @GetMapping("/{shortURL}/stats")
    public ResponseEntity<URLStats> getURLStats(@PathVariable String shortURL) {
        return urlService.getURLStats(shortURL);
    }
}
