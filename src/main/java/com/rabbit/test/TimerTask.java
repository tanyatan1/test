package com.rabbit.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rabbit.service.MailService;
import com.rabbit.utils.MqUtils;

import java.io.IOException;

@Component
public class TimerTask {
    @Autowired
    private MqUtils mqUtils;
    @Autowired
    private MailService mailService;
    
    
    @Scheduled(cron = "0/2 * * * * ? ")
    public void checkUnackTask() throws IOException {
        int delayCount = mqUtils.getMessagesUnacknowledgedCount("delay_queue");
        System.out.println("UnacknowledgedCount of delay_queue: " + delayCount);
        
        int helloCount = mqUtils.getMessagesUnacknowledgedCount("helloQueue");
        System.out.println("UnacknowledgedCount of helloQueue: " + helloCount);
        
        if ((delayCount + helloCount) >= 10) {
          mailService.sendSimpleMail("903907306@qq.com","test simple mail"," hello this is simple mail");
        }
        
    }
}
