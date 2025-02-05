package com.example.rabbitmq.producer;

import com.example.rabbitmq.producer.model.Employee;
import com.example.rabbitmq.producer.producer.EmployeeJsonProducer;
import com.example.rabbitmq.producer.producer.HelloRabbitProducer;
import com.example.rabbitmq.producer.producer.HumanResourceProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

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
	private final EmployeeJsonProducer employeeJsonProducer;
	private final HumanResourceProducer humanResourceProducer;

	@Autowired
	public Application(HelloRabbitProducer helloRabbitProducer, EmployeeJsonProducer employeeJsonProducer,
					   HumanResourceProducer humanResourceProducer){
		this.helloRabbitProducer = helloRabbitProducer;
		this.employeeJsonProducer = employeeJsonProducer;
		this.humanResourceProducer = humanResourceProducer;
	}

	@Override
	public void run(String... args) throws JsonProcessingException {
		//helloRabbitProducer.sendHello("Mohammad");

		//employeeJsonProducer.sendMessage(new Employee("101", "Ali", LocalDate.of(2002,2,25)));
		//employeeJsonProducer.sendMessage(new Employee("102", "Sara", LocalDate.of(1992, 8, 15)));
		//employeeJsonProducer.sendMessage(new Employee("103", "Reza", LocalDate.of(1985, 3, 20)));
		//employeeJsonProducer.sendMessage(new Employee("104", "Mina", LocalDate.of(1995, 12, 5)));
		//employeeJsonProducer.sendMessage(new Employee("105", "Amir", LocalDate.of(1988, 7, 25)));

		humanResourceProducer.sendMessage(new Employee("101", "Ali", LocalDate.of(2002,2,25)));
		humanResourceProducer.sendMessage(new Employee("102", "Sara", LocalDate.of(1992, 8, 15)));
		humanResourceProducer.sendMessage(new Employee("103", "Reza", LocalDate.of(1985, 3, 20)));
		humanResourceProducer.sendMessage(new Employee("104", "Mina", LocalDate.of(1995, 12, 5)));
		humanResourceProducer.sendMessage(new Employee("105", "Amir", LocalDate.of(1988, 7, 25)));

	}

}
