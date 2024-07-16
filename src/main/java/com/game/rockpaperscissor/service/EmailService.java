package com.game.rockpaperscissor.service;


import com.game.rockpaperscissor.exception.EmailSendingException;

/**
 * The EmailService interface provides methods for sending emails.
 */
public interface EmailService {
    void sendEmail(String to, String subject, String text) throws EmailSendingException;
}
