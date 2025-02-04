package com.example.rabbitmq.producer.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloRabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public HelloRabbitProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendHello(String name){
         // convertAndSend :
        //🔹 If your message is a String, Spring converts it to byte[].
        //🔹 If the message is a Java object (Object), Spring uses a Message Converter and serializes it (JSON/XML or binary).
        String message = "Hello, " + name + "!";
        rabbitTemplate.convertAndSend("course.hello", message);
        System.out.println("Message send: " + message);
    }

}
