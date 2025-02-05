package com.example.rabbitmq.consumer.consumer;

import com.example.rabbitmq.consumer.model.Picture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicExchangePictureConsumer {

    private final ObjectMapper objectMapper;
    private final static Logger logger = LoggerFactory.getLogger(PictureVectorConsumer.class);

    @Autowired
    public TopicExchangePictureConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = {"q.picture.image", "q.picture.vector", "q.picture.filter", "q.picture.log"})
    public void listen(Message message) throws JsonProcessingException {
        String jsonString = new String(message.getBody());
        Picture picture = objectMapper.readValue(jsonString, Picture.class);
        logger.info("Consuming : {} with routing key : {}", picture, message.getMessageProperties().getReceivedRoutingKey());
    }

}
