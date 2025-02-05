package com.example.rabbitmq.consumer.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Picture {

    private String name;
    private String type;
    private String source;
    private long size;

}
