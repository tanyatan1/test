package com.rabbit.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class MqUtils {
    private static final Logger logger = LoggerFactory.getLogger(MqUtils.class);

    @Value("${spring.rabbitmq.host}")
    private String host;
//    @Value("${spring.rabbitmq.port}")
    private String port = "15672";
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    /**
     * ������������
     *
     * @param queueName
     * @return
     */
    public int getMessageCount(String queueName) throws IOException {
        String apiMessage = getApiMessage(queueName);
        if (Objects.equals(apiMessage, "")) {
            logger.error("����RabbitMQ APIʱ������");
            return 0;
        }
        JSONObject jsonObject = JSON.parseObject(apiMessage);
        return Integer.parseInt(jsonObject.get("messages").toString());
    }

    /**
     * ����ready������
     *
     * @param queueName
     * @return
     */
    public int getMessageReadyCount(String queueName) throws IOException {
        String apiMessage = getApiMessage(queueName);
        if (Objects.equals(apiMessage, "")) {
            logger.error("����RabbitMQ APIʱ������");
            return 0;
        }
        JSONObject jsonObject = JSON.parseObject(apiMessage);
        return Integer.parseInt(jsonObject.get("messages_ready").toString());
    }

    /**
     * ����unack��MQ
     *
     * @param queueName
     * @return
     */
    public int getMessagesUnacknowledgedCount(String queueName) throws IOException {
        String apiMessage = getApiMessage(queueName);
        if (Objects.equals(apiMessage, "")) {
            logger.error("����RabbitMQ APIʱ������");
            return 0;
        }
        JSONObject jsonObject = JSON.parseObject(apiMessage);
        return Integer.parseInt(jsonObject.get("messages_unacknowledged").toString());
    }

    /**
     * ��ȡ������Ϣ������ready��Ϣ����unack��Ϣ��
     *
     * @param queueName
     * @return Map<String,Integer>
     */
    public Map<String, Integer> getMQCountMap(String queueName) throws IOException {
        String apiMessage = getApiMessage(queueName);
        JSONObject jsonObject = JSON.parseObject(apiMessage);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("messages", Integer.parseInt(jsonObject.get("messages").toString()));
        map.put("messages_ready", Integer.parseInt(jsonObject.get("messages_ready").toString()));
        map.put("messages_unacknowledged", Integer.parseInt(jsonObject.get("messages_unacknowledged").toString()));
        return map;
    }

    public String getApiMessage(String queueName) throws IOException {
        //����һ��GET����
        HttpURLConnection httpConn = null;
        BufferedReader in = null;
        if(virtualHost.equals("/")) {
            virtualHost = URLEncoder.encode(virtualHost);
        }
        String urlString = "http://" + host + ":" + port + "/api/queues/" + virtualHost + "/" + queueName;
        URL url = new URL(urlString);
        httpConn = (HttpURLConnection) url.openConnection();
        //�����û�������
        String auth = username + ":" + password;
        BASE64Encoder enc = new BASE64Encoder();
        String encoding = enc.encode(auth.getBytes());
        httpConn.setDoOutput(true);
        httpConn.setRequestProperty("Authorization", "Basic " + encoding);
        // ����ʵ�ʵ�����
        httpConn.connect();
        //��ȡ��Ӧ
        if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            StringBuilder content = new StringBuilder();
            String tempStr = "";
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            while ((tempStr = in.readLine()) != null) {
                content.append(tempStr);
            }
            in.close();
            httpConn.disconnect();
            return content.toString();
        } else {
            httpConn.disconnect();
            return "";
        }
    }
}


