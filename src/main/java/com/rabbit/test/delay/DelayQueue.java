package com.rabbit.test.delay;


import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DelayQueue {
    /** ��Ϣ������������*/
    public static final String EXCHANGE = "delay";
    /** ����key1*/
    public static final String ROUTINGKEY1 = "delay_key1";
    /** ����key2*/
    public static final String ROUTINGKEY2 = "delay_key2";

    /**
     * ����������Ϣ
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1",5672);

        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true); // ����Ҫ����
        return connectionFactory;
    }

    /**  
     * ������Ϣ������
     * �������������  
        FanoutExchange: ����Ϣ�ַ������еİ󶨶��У���routingkey�ĸ���  
        HeadersExchange ��ͨ���������key-valueƥ��  
        DirectExchange:����routingkey�ַ���ָ������  
        TopicExchange:��ؼ���ƥ��  
     */  
    @Bean  
    public DirectExchange defaultExchange() {  
        return new DirectExchange(EXCHANGE, true, false);
    } 

    /**
     * ������Ϣ����2
     * �������������  
     * @return
     */
    @Bean
    public Queue queue() {  
       return new Queue("delay_queue", true); //���г־�  

    }
    /**
     * ����Ϣ����2�뽻������
     * �������������  
     * @return
     */
    @Bean  
    @Autowired
    public Binding binding() {  
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(DelayQueue.ROUTINGKEY2);  
    } 

    /**
     * ������Ϣ�ļ�������������������Ϣ����2����Ϣ
     * �������������  
     * @return
     */
    @Bean  
    @Autowired
    public SimpleMessageListenerContainer messageContainer2(ConnectionFactory connectionFactory) {  
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());  
        container.setQueues(queue());  
        container.setExposeListenerChannel(true);  
        container.setMaxConcurrentConsumers(1);  
        container.setConcurrentConsumers(1);  
        container.setPrefetchCount(20);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //����ȷ��ģʽ�ֹ�ȷ��  
        container.setMessageListener(new ChannelAwareMessageListener() {

            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                byte[] body = message.getBody();  
                System.out.println("delay_queue �յ���Ϣ : " + new String(body));  
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //ȷ����Ϣ�ɹ�����  

            }  

        });  
        return container;  
    }  


}
