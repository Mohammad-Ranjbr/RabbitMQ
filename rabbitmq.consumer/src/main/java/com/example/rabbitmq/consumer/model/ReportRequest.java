package com.example.rabbitmq.consumer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportRequest {

    private String reportName;
    private boolean large;

}
