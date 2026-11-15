package com.caffeletterario.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import com.caffeletterario.model.Newsletter;
import com.caffeletterario.repository.NewsletterRepository;

@Service
public class NewsletterService {

    private NewsletterRepository newsletterRepository;
    Logger logger = LoggerFactory.getLogger(NewsletterService.class);

    public NewsletterService(NewsletterRepository newsletterRepository) {
        this.newsletterRepository = newsletterRepository;
    }

    // salva o aggiorna una newsletter da email, verificando prima se l'email è
    // gia presente e se è in stato attivo
    public Newsletter saveNewsletter(String email) {
        Newsletter existingNewsletter = newsletterRepository.findByEmail(email);
        if (existingNewsletter == null) {
            Newsletter newsletter = new Newsletter();
            newsletter.setEmail(email);
            newsletter.setSubscribedAt(LocalDateTime.now());
            return newsletterRepository.save(newsletter);
        }
        if (existingNewsletter.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User alredy registerd");
        } else {
            existingNewsletter.setIsActive(true);
            return newsletterRepository.save(existingNewsletter);
        }
    }

    // metodo per la disiscrizione, ricerco per id e imposto il flag IsActive a
    // false
    public void unsubscribeNewsletter(Long id) {
        Newsletter newsletter = newsletterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Newsletter not found with id: " + id));

        newsletter.setIsActive(false);
        newsletter.setUnsubscribedAt(LocalDateTime.now());
        newsletterRepository.save(newsletter);
    }

    public void deleteNewsletter(Newsletter newsletter) {
        newsletterRepository.delete(newsletter);
    }

}
