package com.example.rabbitmq.consumer.consumer;

import com.example.rabbitmq.consumer.rabbitmq.RabbitmqHeader;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.time.LocalDateTime;

public class DlxProcessingErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(DlxProcessingErrorHandler.class);

    @NonNull
    private String deadExchangeName;

    private int maxRetryCount = 3;

    public DlxProcessingErrorHandler(String deadExchangeName) throws IllegalArgumentException {
        super();

        if (deadExchangeName == null || deadExchangeName.length() == 0) {
            throw new IllegalArgumentException("Must define dlx exchange name");
        }

        this.deadExchangeName = deadExchangeName;
    }

    public DlxProcessingErrorHandler(String deadExchangeName, int maxRetryCount) {
        this(deadExchangeName);
        setMaxRetryCount(maxRetryCount);
    }

    public String getDeadExchangeName() {
        return deadExchangeName;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public boolean handleErrorProcessingMessage(Message message, Channel channel, long deliveryTag) {
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
            return true;
        } catch (IOException e) {
            logger.warn("[HANDLER-FAILED] Error at " + LocalDateTime.now() + " on retry "
                    + rabbitMqHeader.getFailedRetryCount() + " for message " + new String(message.getBody()));
        }

        return false;
    }

    public void setMaxRetryCount(int maxRetryCount) throws IllegalArgumentException {
        if (maxRetryCount > 1000) {
            throw new IllegalArgumentException("max retry must between 0-1000");
        }

        this.maxRetryCount = maxRetryCount;
    }

}