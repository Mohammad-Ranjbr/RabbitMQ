package com.example.rabbitmq.producer;

import com.example.rabbitmq.producer.model.Furniture;
import com.example.rabbitmq.producer.model.Picture;
import com.example.rabbitmq.producer.producer.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
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
	private final DirectExchangePictureProducer pictureProducer;
	private final TopicExchangePictureProducer pictureProducerTwo;
	private final MyPictureProducer myPictureProducer;
	private final FurnitureProducer furnitureProducer;
	private final DirectExchangeRetryPictureProducer directExchangeRetryPictureProducer;

	@Override
	public void run(String... args) throws JsonProcessingException {
		//helloRabbitProducer.sendHello("Mohammad");

		//employeeJsonProducer.sendMessage(new Employee("101", "Ali", LocalDate.of(2002,2,25)));
		//employeeJsonProducer.sendMessage(new Employee("102", "Sara", LocalDate.of(1992, 8, 15)));
		//employeeJsonProducer.sendMessage(new Employee("103", "Reza", LocalDate.of(1985, 3, 20)));
		//employeeJsonProducer.sendMessage(new Employee("104", "Mina", LocalDate.of(1995, 12, 5)));
		//employeeJsonProducer.sendMessage(new Employee("105", "Amir", LocalDate.of(1988, 7, 25)));

		//humanResourceProducer.sendMessage(new Employee("101", "Ali", LocalDate.of(2002,2,25)));
		//humanResourceProducer.sendMessage(new Employee("102", "Sara", LocalDate.of(1992, 8, 15)));
		//humanResourceProducer.sendMessage(new Employee("103", "Reza", LocalDate.of(1985, 3, 20)));
		//humanResourceProducer.sendMessage(new Employee("104", "Mina", LocalDate.of(1995, 12, 5)));
		//humanResourceProducer.sendMessage(new Employee("105", "Amir", LocalDate.of(1988, 7, 25)));

		//List<String> sources = Arrays.asList("mobile", "web");
        //List<String> types = Arrays.asList("jpg", "png", "svg");

        //for (int i = 1; i <= 10; i++) {
            //Picture picture = new Picture(
                //"image-" + i,
                //types.get(new Random().nextInt(types.size())),
                //sources.get(new Random().nextInt(sources.size())),
                //new Random().nextInt(10000)
            //);
			//pictureProducer.sendMessage(picture);
        //}

		//List<String> sources = Arrays.asList("mobile", "web");
		//List<String> types = Arrays.asList("jpg", "png", "svg");

		//for (int i = 1; i <= 10; i++) {
			//Picture picture = new Picture(
					//"image-" + i,
					//types.get(new Random().nextInt(types.size())),
					 //sources.get(new Random().nextInt(sources.size())),
					//new Random().nextInt(10000)
			//);
			//pictureProducerTwo.sendMessage(picture);
		//}

		//List<String> sources = Arrays.asList("mobile", "web");
		//List<String> types = Arrays.asList("jpg", "png", "svg");

		//for (int i = 1; i <= 1; i++) {
			//Picture picture = new Picture(
					//"image-" + i,
					//types.get(new Random().nextInt(types.size())),
					//sources.get(new Random().nextInt(sources.size())),
					//new Random().nextInt(9001,10000)
			//);
			//myPictureProducer.sendMessage(picture);
		//}

		//List<String> colors = Arrays.asList("red", "white", "green");
		//List<String> materials = Arrays.asList("wood", "plastic", "steel");

		//for (int i = 1; i <= 10; i++) {
			//Furniture furniture = new Furniture(
					//"Furniture-" + i,
					//colors.get(new Random().nextInt(colors.size())),
					//materials.get(new Random().nextInt(materials.size())),
					//new Random().nextInt(10000,100000)
			//);
			//furnitureProducer.sendMessage(furniture);
		//}

		List<String> sources = Arrays.asList("mobile", "web");
		List<String> types = Arrays.asList("jpg", "png", "svg");

		for (int i = 1; i <= 10; i++) {
			Picture picture = new Picture(
					"image-" + i,
					types.get(new Random().nextInt(types.size())),
					 sources.get(new Random().nextInt(sources.size())),
					new Random().nextInt(10000)
			);
			directExchangeRetryPictureProducer.sendMessage(picture);
		}

	}

}
