package com.game.rockpaperscissor.service.impl;

import com.game.rockpaperscissor.exception.EmailSendingException;
import com.game.rockpaperscissor.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The EmailServiceImpl class is an implementation of the EmailService interface.
 * It provides functionality to send emails using the JavaMailSender.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends an email to the specified recipient with the given subject and text.
     * @param to
     * @param subject
     * @param text
     */
    @Override
    public void sendEmail(String to, String subject, String text) throws EmailSendingException {
        logger.info("Sending email to: {} with subject: {}", to, subject);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            logger.info("Attempting to send email...");
            mailSender.send(message);
            logger.info("Email sent to: {}", to);
        } catch (MailException e) {
            logger.error("Failed to send email to: {} with subject: {}", to, subject, e);
            throw new EmailSendingException("Failed to send email to: " + to, e);
        }
    }
}
