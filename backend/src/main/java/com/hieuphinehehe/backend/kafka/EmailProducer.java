package com.hieuphinehehe.backend.kafka;

import com.hieuphinehehe.backend.dto.response.DataMailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducer {
    private final KafkaTemplate<String, DataMailDTO> kafkaTemplate;

    public void sendEmailMessage(DataMailDTO dataMail) {
        kafkaTemplate.send("email-topic", dataMail);
    }
}