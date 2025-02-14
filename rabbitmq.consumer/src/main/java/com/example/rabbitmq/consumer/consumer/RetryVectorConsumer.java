package com.example.rabbitmq.consumer.consumer;

import java.io.IOException;

import com.example.rabbitmq.consumer.model.Picture;
import com.example.rabbitmq.consumer.rabbitmq.DlxProcessingErrorHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;

import org.springframework.messaging.handler.annotation.Header;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetryVectorConsumer {

    private final ObjectMapper objectMapper;
    private static final String DEAD_EXCHANGE_NAME = "x.guideline.dead";
    private static final Logger logger = LoggerFactory.getLogger(RetryVectorConsumer.class);
    private final DlxProcessingErrorHandler dlxProcessingErrorHandler = new DlxProcessingErrorHandler(DEAD_EXCHANGE_NAME);;

    //@RabbitListener(queues = "q.guideline.vector.work")
    public void listen(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag)
            throws InterruptedException, JsonParseException, JsonMappingException, IOException {
        try {
            var p = objectMapper.readValue(message.getBody(), Picture.class);
            // process the image
            if (p.getSize() > 9000) {
                // throw exception, we will use DLX handler for retry mechanism
                dlxProcessingErrorHandler.handleErrorProcessingMessage(message, channel, deliveryTag);
            } else {
                logger.info("Convert to image, creating thumbnail, & publishing : " + p);
                // you must acknowledge that message already processed
                channel.basicAck(deliveryTag, false);
            }
        } catch (IOException e) {
            logger.warn("Error processing message : " + new String(message.getBody()) + " : " + e.getMessage());
            dlxProcessingErrorHandler.handleErrorProcessingMessage(message, channel, deliveryTag);
        }
    }

}
