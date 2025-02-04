package com.example.rabbitmq.producer;

import com.example.rabbitmq.producer.producer.HelloRabbitProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

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
