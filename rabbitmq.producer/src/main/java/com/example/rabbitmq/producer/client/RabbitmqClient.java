package com.example.rabbitmq.producer.client;

import com.example.rabbitmq.producer.model.RabbitmqQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Base64;
import java.util.List;

@Service
public class RabbitmqClient {

    // To communicate with RabbitMQ API, we need a helper class.
    // We include the RabbitMQClient.java class in the client package.
    // This class should have a method to get the list of queues.

    // The getAllQueues method
    // This method gets the list of all RabbitMQ queues from the API.
    // To make a request to RabbitMQ, we need a WebClient.
    // Also, since the API requires authentication, we need to create a method to create the authentication headers (Basic Auth).

    // WebFlux is the Reactive version of the Spring framework for handling web requests. Unlike RestTemplate, which works in a blocking (synchronous) manner, WebClient in WebFlux is non-blocking (asynchronous). This makes the application more efficient when faced with large requests.
    // It handles concurrent requests better without blocking threads.

    private final WebClient webClient;

    @Autowired
    public RabbitmqClient(WebClient webClient){
        this.webClient = webClient;
    }

    public List<RabbitmqQueue> getAllQueues(){
        // Sends a GET request to retrieve information.
        return webClient.get()
                .uri("/queues")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RabbitmqQueue>>() {})
                .block(Duration.ofSeconds(10)); // It waits for a response (Blocking Call) and if no response is received within 10 seconds, an exception occurs.
    }

}
