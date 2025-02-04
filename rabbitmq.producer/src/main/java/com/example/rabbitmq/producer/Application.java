package com.example.rabbitmq.producer;

import com.example.rabbitmq.producer.producer.HelloRabbitProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Application implements CommandLineRunner {

	// What is @EnableScheduling?
	//🔹 We place this annotation on the main Spring Boot class to enable scheduling.
	//🔹 Without this annotation, methods with @Scheduled will not execute.

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private final HelloRabbitProducer helloRabbitProducer;

	@Autowired
	public Application(HelloRabbitProducer helloRabbitProducer){
		this.helloRabbitProducer = helloRabbitProducer;
	}

	@Override
	public void run(String... args){
		helloRabbitProducer.sendHello("Mohammad");
	}
}
