package com.game.rockpaperscissor.service;

import com.game.rockpaperscissor.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    public void testSendEmailWhenCalledWithValidArgumentsThenEmailIsSent() {
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Text";

        emailService.sendEmail(to, subject, text);
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(to);
        expectedMessage.setSubject(subject);
        expectedMessage.setText(text);
        verify(mailSender, times(1)).send(expectedMessage);
    }
}
