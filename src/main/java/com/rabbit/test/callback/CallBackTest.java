package com.rabbit.test.callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbit.test.callback.CallBackSender;
import com.rabbit.test.hello.HelloSender1;
import com.rabbit.test.user.UserSender;

@RestController
@RequestMapping("/rabbit")
public class CallBackTest {
    @Autowired
    private CallBackSender callBackSender;
    
    @PostMapping("/callback")
    public void callbak() {
        callBackSender.send();
    }
}