package com.caffeletterario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caffeletterario.service.NewsletterService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/subscription")
public class NewsletterController {
    private final NewsletterService newsletterService;

    public NewsletterController(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> saveSubscriberEmail(@RequestBody String email) {
        newsletterService.saveNewsletter(email);
        return ResponseEntity.ok("User subscribed: " + email);
    }

    @PutMapping("/unsubscribe/{id}")
    public ResponseEntity<String> putMethodName(@PathVariable Long id) {
        newsletterService.unsubscribeNewsletter(id);
        return ResponseEntity.ok("User unsubscribed");
    }



}
