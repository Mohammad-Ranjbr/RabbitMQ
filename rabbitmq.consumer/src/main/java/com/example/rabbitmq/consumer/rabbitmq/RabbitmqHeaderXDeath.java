package com.example.rabbitmq.consumer.rabbitmq;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class RabbitmqHeaderXDeath {

    private int count;
    private String exchange;
    private String queue;
    private String reason;
    private List<String> routingKey;
    private Date time;

}
