package com.example.rabbitmq.producer.producer;

import com.example.rabbitmq.producer.model.Furniture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FurnitureProducer {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public FurnitureProducer(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate){
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Furniture furniture) throws JsonProcessingException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("color", furniture.getColor());
        messageProperties.setHeader("material", furniture.getMaterial());

        String jsonMessage = objectMapper.writeValueAsString(furniture);
        Message message = new Message(jsonMessage.getBytes(), messageProperties);

        rabbitTemplate.send("x.promotion", "", message);
        System.out.println("Send message : " + jsonMessage);
    }

}
