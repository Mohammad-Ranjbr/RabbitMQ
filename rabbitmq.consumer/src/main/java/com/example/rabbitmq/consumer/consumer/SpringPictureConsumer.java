package com.example.rabbitmq.consumer.consumer;

import com.example.rabbitmq.consumer.model.Picture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SpringPictureConsumer {

    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(SpringPictureConsumer.class);

    @Autowired
    public SpringPictureConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "q.spring.image.work")
    public void listenImage(String message) throws IOException {
        Picture picture = objectMapper.readValue(message, Picture.class);
        logger.info("Consuming image : {}", picture);
        if(picture.getSize() > 9000){
            throw new IOException("Image " + picture.getName() + " size too large : " + picture.getSize());
        }
        logger.info("Processing image : " + picture);
    }

    @RabbitListener(queues = "q.spring.vector.work")
    public void listenVector(String message) throws JsonProcessingException {
        Picture picture = objectMapper.readValue(message, Picture.class);
        logger.info("Consuming vector : {}", picture);
        logger.info("Processing vector : " + picture);
    }

}
