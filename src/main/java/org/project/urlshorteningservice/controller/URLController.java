package org.project.urlshorteningservice.controller;

import lombok.AllArgsConstructor;
import org.project.urlshorteningservice.model.URLResponse;
import org.project.urlshorteningservice.model.URLStats;
import org.project.urlshorteningservice.service.URLService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@Controller
@AllArgsConstructor
public class URLController implements ErrorController {

    private final URLService urlService;

    @PostMapping("/createURL")
    public ResponseEntity<URLResponse> createURL(@RequestBody String longURL) {
        return urlService.shortenURL(longURL);
    }

    @GetMapping("/retrieve")
    public ResponseEntity<URLResponse> retrieveLongURL(@RequestParam String shortURL) {
        return urlService.retrieveLongURL(shortURL);
    }

    @PutMapping("/update")
    public ResponseEntity<URLResponse> updateShortURL(@RequestParam String shortURL, @RequestBody String longURL) {
        return urlService.updateShortURL(shortURL, longURL);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteShortURL(@RequestParam String shortURL) {
        return urlService.deleteShortURL(shortURL);
    }

    @GetMapping("/stats")
    public ResponseEntity<URLStats> getURLStats(@RequestParam String shortURL) {
        return urlService.getURLStats(shortURL);
    }

    // redirecting to longURL address
    @RequestMapping("/{shortURL}")
    public HttpResponse<String> redirect(@PathVariable String shortURL) {
        return urlService.redirect(shortURL);
    }

    @RequestMapping("/error")
    public ResponseEntity<String> error() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such endpoint");
    }
}
