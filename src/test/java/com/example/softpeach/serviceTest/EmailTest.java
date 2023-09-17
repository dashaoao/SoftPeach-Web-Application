package com.example.softpeach.serviceTest;

import com.example.softpeach.services.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

public class EmailTest {

    @Mock
    private JavaMailSender mailSender;
    private EmailService emailService;

    public EmailTest() {
        MockitoAnnotations.openMocks(this);
        emailService = new EmailService(mailSender);
    }

    @Test
    public void testSendMessage() {
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Message";

        emailService.sendMessage(to, subject, text);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}