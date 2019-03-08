package com.rabbit.test.hello;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloSender1 {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
    * ���췽��ע��rabbitTemplate
    */
//    @Autowired
//    public HelloSender1(RabbitTemplate rabbitTemplate) {
//    this.rabbitTemplate = rabbitTemplate;
//    rabbitTemplate.setConfirmCallback(this); //rabbitTemplate���Ϊ�����Ļ����ǻص�����������õ�����
//    }
    
    public void send() {
        String sendMsg = "hello1 " + new Date();
        System.out.println("Sender1 : " + sendMsg);
        this.rabbitTemplate.convertAndSend("helloQueue", sendMsg);
    }
    
    public void send(String msg) {
        String sendMsg = msg + new Date();
        System.out.println("Sender1 : " + sendMsg);
        this.rabbitTemplate.convertAndSend("helloQueue", sendMsg);
    }

}