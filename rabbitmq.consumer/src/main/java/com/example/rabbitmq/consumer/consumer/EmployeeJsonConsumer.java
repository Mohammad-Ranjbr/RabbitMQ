package com.example.rabbitmq.consumer.consumer;

import com.example.rabbitmq.consumer.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeJsonConsumer {

    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeJsonConsumer.class);

    @Autowired
    public EmployeeJsonConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "course.employee")
    public void listen(String message) throws JsonProcessingException {
        Employee employee = objectMapper.readValue(message, Employee.class);
        logger.info("Employee is : " + employee);
    }

}
