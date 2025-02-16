package com.example.rabbitmq.producer.producer;

import com.example.rabbitmq.producer.model.ReportRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportRequestProducer {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ReportRequestProducer(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate){
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    // We create an exchange called x.delayed with type x-delayed-message and add an attribute x-delayed-type: direct.

    public void sendMessage(ReportRequest reportRequest) throws JsonProcessingException {
        MessageProperties messageProperties = new MessageProperties();
        long delayInMillis = 0;

        if(reportRequest.isLarge()){
            delayInMillis = 2 * 60 * 1000;
        }
        messageProperties.setHeader("x-delay", delayInMillis);
        String jsonMessage = objectMapper.writeValueAsString(reportRequest);
        Message message = new Message(jsonMessage.getBytes(), messageProperties);
        rabbitTemplate.send("x.delayed", "delayThis", message);
    }

}
