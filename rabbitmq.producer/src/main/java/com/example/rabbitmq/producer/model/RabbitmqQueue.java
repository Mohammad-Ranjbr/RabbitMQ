package com.example.rabbitmq.producer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // We ignore other features using
public class RabbitmqQueue {

    // The RabbitMQ API returns queue information in JSON.
    // So we create a Java class to process this JSON.

    private long messages; // Number of messages in queue
    private String name; // Queue name

    public boolean isDirty(){
        return messages > 0;
    }

}
