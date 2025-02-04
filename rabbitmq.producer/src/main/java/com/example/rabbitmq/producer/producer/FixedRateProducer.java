package com.example.rabbitmq.producer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FixedRateProducer {

    private int i = 0;
    private final RabbitTemplate rabbitTemplate;
    private final static Logger logger = LoggerFactory.getLogger(FixedRateProducer.class);

    @Autowired
    public FixedRateProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    // 🔹 This annotation is placed on a method so that the method is executed every 500 milliseconds (half a second).
    //🔹 The fixedRate value means how many milliseconds the method is executed, regardless of whether the previous execution has finished or not.
    //@Scheduled(fixedRate = 500)
    public void sendMessage(){
        i++;
        logger.info("i is : " + i);
        rabbitTemplate.convertAndSend("course.fixedrate", "Fixed rate : " + i);
    }

}
