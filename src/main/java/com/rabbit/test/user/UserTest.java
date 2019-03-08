package com.rabbit.test.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class UserTest {
  @Autowired
  private UserSender userSender;

  @PostMapping("/user")
  public void hello() {
      userSender.send();
  }
}