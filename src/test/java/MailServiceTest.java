import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.rabbit.service.MailService;
import com.rabbit.service.impl.MailServiceImpl;


/**
 * 
 * @author tyro
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MailService.class)
public class MailServiceTest {

    private MailService mailService = new MailServiceImpl();

    private TemplateEngine templateEngine = new SpringTemplateEngine();

    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail("ying.tan@pactera.com","test simple mail"," hello this is simple mail");
    }

}