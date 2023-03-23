package com.musicat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicat.data.entity.Music;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void sendKafkaMessage(String topic, Object object) {
        try {
            String jsonString = objectMapper.writeValueAsString((Music)object);
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, jsonString);

            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    System.out.println("Sent message=[" + jsonString + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                }

                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("Unable to send message=[" + jsonString + "] due to: " + ex.getMessage());
                }
            });
        } catch (JsonProcessingException e) {
            System.out.println("Error converting object to JSON: " + e.getMessage());
        }
    }
}