package com.caffeletterario.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.caffeletterario.model.Newsletter;
import com.caffeletterario.repository.NewsletterRepository;

@Service
public class NewsletterService {

    private NewsletterRepository newsletterRepository;

    public NewsletterService(NewsletterRepository newsletterRepository) {
        this.newsletterRepository = newsletterRepository;
    }

    public Newsletter getNewsletterByEmail(String email) {
        return newsletterRepository.findByEmail(email);
    }

    public void saveNewsletter(String email) {
        Newsletter newsletter = new Newsletter(email);
        newsletterRepository.save(newsletter);
    }

    public void deleteNewsletter(Newsletter newsletter) {
        newsletterRepository.delete(newsletter);
    }

    public void unsubscribeNewsletter(Long id) {
        Newsletter newsletter = newsletterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Newsletter not found with id: " + id));

        newsletter.setIsActive(false);
        newsletter.setUnsubscribedAt(LocalDateTime.now());
        newsletterRepository.save(newsletter);
    }
}
