package com.example.rabbitmq.producer.producer;

import com.example.rabbitmq.producer.model.Picture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPictureProducer {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MyPictureProducer(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate){
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Picture picture) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(picture);
        rabbitTemplate.convertAndSend("x.mypicture", picture.getType(), jsonMessage);
        System.out.println("Send message: " + jsonMessage);
    }

}
