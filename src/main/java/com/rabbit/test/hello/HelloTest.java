package com.rabbit.test.hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class HelloTest {
  @Autowired
  private HelloSender1 helloSender1;

  /**
   * oneToOne
   */
  @PostMapping("/oneToOne")
  public void hello() {
      helloSender1.send();
  }
  
  /**
   * oneToMany
   */
  @PostMapping("/oneToMany")
  public void oneToMany() {
      for(int i=0;i<10;i++){
          helloSender1.send("hellomsg:"+i);
      }
  }
      
}