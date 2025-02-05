package com.example.rabbitmq.consumer.consumer;

import com.example.rabbitmq.consumer.model.Picture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PictureVectorConsumer {

    private final ObjectMapper objectMapper;
    private final static Logger logger = LoggerFactory.getLogger(PictureVectorConsumer.class);

    @Autowired
    public PictureVectorConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "q.picture.vector")
    public void listen(String message) throws JsonProcessingException {
        Picture picture = objectMapper.readValue(message, Picture.class);
        logger.info("Picture in vector queue : " + picture);
    }

}
