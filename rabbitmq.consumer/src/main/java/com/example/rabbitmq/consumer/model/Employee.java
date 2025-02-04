package com.example.rabbitmq.consumer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Employee {

    // Jackson requires a No-Args Constructor to convert JSON to a Java object (Deserialization).

    @JsonProperty("employee_id")
    private String id;
    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

}
