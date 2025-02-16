package com.example.rabbitmq.consumer.consumer;

import com.example.rabbitmq.consumer.model.ReportRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportRequestConsumer {

    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(ReportRequestConsumer.class);

    @Autowired
    public ReportRequestConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    //@RabbitListener(queues = "q.delayed")
    public void listen(String message) throws JsonProcessingException {
        ReportRequest reportRequest = objectMapper.readValue(message, ReportRequest.class);
        logger.info("On repost request {}", reportRequest);
    }

}
