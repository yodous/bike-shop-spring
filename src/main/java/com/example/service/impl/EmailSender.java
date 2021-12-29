package com.example.service.impl;

import com.example.model.abstracts.Message;
import com.example.service.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSender implements MessageSender {
    private final JavaMailSender mailSender;

//    @Value("${server.port}")
//    private int port;
    @Value("${email.sender}")
    private String from;
    @Value("${email.subject}")
    private String subject;
    @Value("${email.text}")
    private String text;

    @Override
    public void sendMessage(Message message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(message.getTo());
        simpleMailMessage.setSubject(subject);
        String activationUrl = generatedActivationBaseUrl() + message.getToken();
        simpleMailMessage.setText(activationUrl);

        mailSender.send(simpleMailMessage);
        log.info("email has been sent\n" + activationUrl);
    }

    private String generatedActivationBaseUrl() {
        return String.format("localhost:%d/account-verification?token=", 4200);
    }

    static class ActivationEmail extends Message {
        private String recipientAddressEmail;
        private String activationToken;

        public ActivationEmail(String recipientAddressEmail, String activationToken) {
            super(recipientAddressEmail, activationToken);
        }
    }
}
