package com.example.rabbitmq.consumer.consumer;

import com.example.rabbitmq.consumer.model.Picture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MyPictureConsumer {

    private final ObjectMapper objectMapper;
    private final static Logger logger = LoggerFactory.getLogger(PictureVectorConsumer.class);

    @Autowired
    public MyPictureConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    // So far we have learned that invalid messages can be sent directly to the Dead Letter Exchange (DLX) using an exception (AmqpRejectAndDontRequeueException).
    // But the alternative solution is to manually reject the message instead of throwing an exception. To do this, we need to use manual Acknowledgement.
    // Changing the consumer to manually reject messages
    //🔹 Instead of throwing an exception, we use the channel.basicReject() method to reject the message.
    //🔹 We also manually acknowledge valid messages (basicAck).
    //🔹 To do this, we need to add two parameters channel and deliveryTag to the consumer method.

    //@RabbitListener(queues = "q.mypicture.image")
    public void listen(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        // 🔹 Message message → The message received from RabbitMQ (received as bytes).
        //🔹 Channel channel → Used to handle the confirmation, rejection, or rollback of a message in RabbitMQ.
        //🔹 @Header(AmqpHeaders.DELIVERY_TAG) long tag → The unique identifier of the message to handle its confirmation or rejection.
        String jsonString = new String(message.getBody());
        Picture picture = objectMapper.readValue(jsonString, Picture.class);
        if(picture.getSize() > 9000){
            // What is AmqpRejectAndDontRequeueException in RabbitMQ?
            // In Spring AMQP, when a consumer encounters an error while processing a message, it will requeue the message by default to requeue it.
            // But in some cases, we don't want the message to be requested, because it will always cause an error (for example, the message format is wrong or the image size is too large).
            //🔹 In these situations, we can throw an exception (AmqpRejectAndDontRequeueException).
            //🔹 This exception causes the message to not be requested and go to the Dead Letter Exchange (DLX) (if DLX is configured).
            //throw new AmqpRejectAndDontRequeueException("Image size too large : " + picture);
            channel.basicReject(tag, false); // false means the message will not be added back to the main queue.
        }
        logger.info("Processing image {}", picture);
        channel.basicAck(tag, false);
    }

}
