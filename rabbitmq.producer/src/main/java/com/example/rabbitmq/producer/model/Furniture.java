package com.example.rabbitmq.producer.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Furniture {

    private String name;
    private String color;
    private String material;
    private int price;

}
