package com.example.service.impl;

import com.example.model.abstracts.Message;
import com.example.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendMessage(Message message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(message.getFrom());
        simpleMailMessage.setTo(message.getTo());
        simpleMailMessage.setSubject(message.getSubject());
        simpleMailMessage.setText(message.getText());

        mailSender.send(simpleMailMessage);
    }
}
