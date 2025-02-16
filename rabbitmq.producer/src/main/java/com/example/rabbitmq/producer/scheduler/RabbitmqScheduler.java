package com.example.rabbitmq.producer.scheduler;

import com.example.rabbitmq.producer.client.RabbitmqClient;
import com.example.rabbitmq.producer.model.RabbitmqQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitmqScheduler {

    private final RabbitmqClient rabbitmqClient;
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqScheduler.class);

    @Autowired
    public RabbitmqScheduler(RabbitmqClient rabbitmqClient){
        this.rabbitmqClient = rabbitmqClient;
    }

//    @Scheduled(fixedDelay = 90000)
//    public void sweepDirtyQueues(){
//        try{
//            List<RabbitmqQueue> dirtyQueues = rabbitmqClient.getAllQueues().stream().filter(RabbitmqQueue::isDirty)
//                    .toList();
//            dirtyQueues.forEach(q -> logger.info("Queue {} has {} unprocessed messages.", q.getName(), q.getMessages()));
//        } catch (Exception exception){
//            logger.warn("Cannot sweep queues : " + exception.getMessage());
//        }
//    }

}
