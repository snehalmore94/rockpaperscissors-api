package com.game.rockpaperscissor.service;


/**
 * The EmailService interface provides methods for sending emails.
 */
public interface EmailService {
    void sendEmail(String to, String subject, String text) ;
}
