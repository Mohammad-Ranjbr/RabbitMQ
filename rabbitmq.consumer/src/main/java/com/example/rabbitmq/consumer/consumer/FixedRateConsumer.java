package com.example.rabbitmq.consumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class FixedRateConsumer {

    private static final Logger logger = LoggerFactory.getLogger(FixedRateConsumer.class);

//    @RabbitListener(queues = "course.fixedrate")
//    public void listen(String message){
//        logger.info(message);
//    }

    @RabbitListener(queues = "course.fixedrate", concurrency = "3-7")
    public void listen(String message) throws InterruptedException {
        int delay = ThreadLocalRandom.current().nextInt(500, 2000);
        Thread.sleep(delay);
        logger.info("Processed '{}' in thread {}", message, Thread.currentThread().getName());
    }

}
