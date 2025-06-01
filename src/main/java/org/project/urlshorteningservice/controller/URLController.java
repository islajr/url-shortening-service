package org.project.urlshorteningservice.controller;

import lombok.AllArgsConstructor;
import org.project.urlshorteningservice.model.URLResponse;
import org.project.urlshorteningservice.model.URLStats;
import org.project.urlshorteningservice.service.URLService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class URLController {

    private final URLService urlService;

    @PostMapping("/createURL")
    public String createURL(@RequestParam("longURL") String longURL, Model model) {
        ResponseEntity<URLResponse> response =  urlService.shortenURL(longURL);
        assert response.getBody() != null;
        model.addAttribute("longURL", longURL);
        model.addAttribute("shorturl", response.getBody().shortURL());
        return "results";
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

    @GetMapping("/{shortURL}/stats")
    public ResponseEntity<URLStats> getURLStats(@PathVariable String shortURL) {
        return urlService.getURLStats(shortURL);
    }
}
