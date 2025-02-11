package com.example.rabbitmq.consumer.rabbitmq;

import java.io.IOException;
import java.time.LocalDateTime;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

// Its job is to decide whether to retry a message or send it to the dead message queue (DLQ) if it has been processed multiple times and still fails.
// How does DlxProcessingErrorHandler work?
// Checks how many times a message has failed.
// If the number of failures exceeds the limit (maxRetryCount):
// Sends the message to the Dead Letter Exchange (DLX).
// ACKs the message so that it cannot be sent again.
// If the number of failures is less than the allowed limit:
// Rejects the message to be processed again.

import com.rabbitmq.client.Channel;

@Getter
public class DlxProcessingErrorHandler {

    private int maxRetryCount = 3;
    private final String deadExchangeName;
    private static final Logger logger = LoggerFactory.getLogger(DlxProcessingErrorHandler.class);

    public DlxProcessingErrorHandler(String deadExchangeName) throws IllegalArgumentException {
        if (deadExchangeName == null || deadExchangeName.isEmpty()) {
            throw new IllegalArgumentException("Must define dlx exchange name");
        }
        this.deadExchangeName = deadExchangeName;
    }

    public DlxProcessingErrorHandler(String deadExchangeName, int maxRetryCount) {
        this(deadExchangeName);
        setMaxRetryCount(maxRetryCount);
    }

    public void handleErrorProcessingMessage(Message message, Channel channel, long deliveryTag) {
        var rabbitMqHeader = new RabbitmqHeader(message.getMessageProperties().getHeaders());
        try {
            if (rabbitMqHeader.getFailedRetryCount() >= maxRetryCount) {
                // publish to dead and ack
                logger.warn("[DEAD] Error at " + LocalDateTime.now() + " on retry " + rabbitMqHeader.getFailedRetryCount()
                        + " for message " + new String(message.getBody()));

                channel.basicPublish(getDeadExchangeName(), message.getMessageProperties().getReceivedRoutingKey(),
                        null, message.getBody());
                channel.basicAck(deliveryTag, false);
            } else {
                logger.warn("[REQUEUE] Error at " + LocalDateTime.now() + " on retry "
                        + rabbitMqHeader.getFailedRetryCount() + " for message " + new String(message.getBody()));
                channel.basicReject(deliveryTag, false);
            }
        } catch (IOException e) {
            logger.warn("[HANDLER-FAILED] Error at " + LocalDateTime.now() + " on retry "
                    + rabbitMqHeader.getFailedRetryCount() + " for message " + new String(message.getBody()));
        }
    }

    public void setMaxRetryCount(int maxRetryCount) throws IllegalArgumentException {
        if (maxRetryCount > 1000) {
            throw new IllegalArgumentException("max retry must between 0-1000");
        }
        this.maxRetryCount = maxRetryCount;
    }

}