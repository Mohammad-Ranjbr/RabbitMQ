package com.example.rabbitmq.producer.producer;

import com.example.rabbitmq.producer.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HumanResourceProducer {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public HumanResourceProducer(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate){
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Employee employee) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(employee);
        // Fanout Exchange does not require a Routing Key, so leave it blank.
        rabbitTemplate.convertAndSend("x.hr", "", jsonMessage);
        System.out.println("Send message: " + jsonMessage);
    }

}
