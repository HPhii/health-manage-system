package com.hieuphinehehe.backend.kafka;

import com.hieuphinehehe.backend.dto.response.DataMailDTO;
import com.hieuphinehehe.backend.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {
    private final MailService mailService;

    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void listen(DataMailDTO dataMail) {
        log.info("Received message: {}", dataMail);
        try {
            mailService.sendHTMLMail(dataMail, dataMail.getTemplate());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}