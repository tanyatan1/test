package com.rabbit.test.delay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.amqp.core.MessageProperties;

import org.springframework.amqp.core.Message;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.core.AmqpTemplate;

@Component
public class DelayQueueTest {
    
    @Autowired
    private AmqpTemplate amqpTemplate;
    
    @Scheduled(cron = "0/1 * * * * ? ") 
    public void sendToDelayTask() {
        for (int i=0; i<20; ++i) {
            String msg = "delay " + new Date();
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setExpiration("5000"); //5 seconds
            messageProperties.setCorrelationId(UUID.randomUUID().toString().getBytes());
            Message message = new Message(msg.getBytes(), messageProperties);
            amqpTemplate.convertAndSend(DelayQueue.EXCHANGE, DelayQueue.ROUTINGKEY1,message);
        }
        //use this to test unack message
//        for (int i=0; i<20; ++i) {
//            String msg = "delay " + new Date();
//            MessageProperties messageProperties = new MessageProperties();
//            messageProperties.setExpiration("5000"); //5 seconds
//            messageProperties.setCorrelationId(UUID.randomUUID().toString().getBytes());
//            Message message = new Message(msg.getBytes(), messageProperties);
//            amqpTemplate.convertAndSend(DelayQueue.EXCHANGE, DelayQueue.ROUTINGKEY2,message);
//        }
    }
    
}
