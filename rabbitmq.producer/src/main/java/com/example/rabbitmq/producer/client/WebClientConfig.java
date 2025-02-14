package com.example.rabbitmq.producer.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Configuration
public class WebClientConfig {

    // Creates a new WebClient that connects to the RabbitMQ Management API at http://localhost:15672/api/queues.
    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:15672/api/queues")
                .defaultHeader(HttpHeaders.AUTHORIZATION, createBasicAuthHeaders())
                .build();
    }

    private String createBasicAuthHeaders(){
        var auth = "admin:admin123";
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

}
