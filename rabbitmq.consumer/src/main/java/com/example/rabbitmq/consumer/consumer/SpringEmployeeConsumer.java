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
public class SpringEmployeeConsumer {

    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(SpringEmployeeConsumer.class);

    @Autowired
    public SpringEmployeeConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "q.spring2.accounting.work")
    public void listenAccounting(String message) throws JsonProcessingException {
        Employee employee = objectMapper.readValue(message, Employee.class);
        logger.info("Consuming accounting : " + employee);
        if(employee.getName() == null || employee.getName().isEmpty()){
            throw new IllegalArgumentException("Name is empty !");
        }
        logger.info("On accounting : " + employee);
    }

    @RabbitListener(queues = "q.spring2.marketing.work")
    public void listenMarketing(String message) throws JsonProcessingException {
        Employee employee = objectMapper.readValue(message, Employee.class);
        logger.info("Consuming marketing : " + employee);
        logger.info("On marketing : " + employee);
    }

}
