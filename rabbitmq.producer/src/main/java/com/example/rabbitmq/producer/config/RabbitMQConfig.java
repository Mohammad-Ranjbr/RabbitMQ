package com.example.rabbitmq.producer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // ObjectMapper is used to convert Java Objects to JSON and vice versa.
    //🔹 JsonMapper.builder().findAndAddModules().build() is used to create an instance of ObjectMapper with support for various modules.
    //🔹 This line creates a new ObjectMapper and automatically loads all the related modules.
    //🔹 These modules include date support (JavaTimeModule), JSON format, and other features related to data serialization and deserialization.
    // Why is findAndAddModules() used?
    // In new versions of Jackson, some time-related features (LocalDate, LocalDateTime) and other features are provided as separate modules.
    // 📌 findAndAddModules() automatically finds and adds these modules.
    // For example, if we want to convert a LocalDate object to JSON, we need the JavaTimeModule module. Using findAndAddModules(), this module is added automatically.

    @Bean
    public ObjectMapper getObjectMapper(){
        return JsonMapper.builder().findAndAddModules().build();
    }

}
