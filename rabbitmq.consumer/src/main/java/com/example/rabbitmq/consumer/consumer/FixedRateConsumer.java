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

    // If the Producer sends messages quickly but the Consumer takes a long time to process each message, many messages will remain in the Queue, reducing RabbitMQ's performance.
    // 📌 Problem: Fast production, slow consumption!
    //🔹 Imagine a Producer sending a message every 0.5 seconds.
    //🔹 But the Consumer takes 1.5 to 2 seconds to process each message.
    //🔹 After a few seconds, the queue will fill up with unprocessed messages and RabbitMQ will slow down.
    //🔹 Solution: Add multiple consumers
    // ✅ Instead of using one slow consumer, we add multiple concurrent consumers.
    // ✅ Each consumer processes a different message and the pressure on the system is reduced.
    // concurrency = "3-7" means a minimum of 3 and a maximum of 7 concurrent consumers.
    // Each consumer will wait randomly between 500 and 2000 milliseconds to process the message.
    // Spring Boot automatically manages the execution of threads.
    //@RabbitListener(queues = "course.fixedrate", concurrency = "3-7")
    public void listen(String message) throws InterruptedException {
        int delay = ThreadLocalRandom.current().nextInt(500, 2000);
        Thread.sleep(delay);
        logger.info("Processed '{}' in thread {}", message, Thread.currentThread().getName());
    }

}
