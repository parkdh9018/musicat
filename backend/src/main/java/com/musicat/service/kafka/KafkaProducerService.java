package com.musicat.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  public void send(String topic, Object object) throws JsonProcessingException {
    String jsonString = objectMapper.writeValueAsString(object);
    System.out.println(jsonString);
    kafkaTemplate.send(topic, jsonString);
  }
}