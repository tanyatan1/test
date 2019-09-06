package com.rabbit.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.rabbit.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    /**
     * �����ı��ʼ�
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ying.tan@pactera.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("���ʼ��Ѿ����͡�");
        } catch (Exception e) {
            
        }

    }

    /**
     * ����html�ʼ�
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true��ʾ��Ҫ����һ��multipart message
            MimeMessageHelper helper = null;
            helper.setFrom("903907306@qq.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            logger.info("html�ʼ����ͳɹ�");
        } catch (MessagingException e) {
            logger.error("����html�ʼ�ʱ�����쳣��", e);
        }
    }


    /**
     * ���ʹ��������ʼ�
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath){
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("903907306@qq.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            logger.info("���������ʼ��Ѿ����͡�");
        } catch (MessagingException e) {
            logger.error("���ʹ��������ʼ�ʱ�����쳣��", e);
        } finally {
		}
    }


    /**
     * �����������о�̬��Դ��ͼƬ�����ʼ�
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("903907306@qq.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            System.out.println("content="+content);
            System.out.println("rscId="+rscId);
            System.out.println("rscPath="+rscPath);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);
            mailSender.send(message);
            logger.info("Ƕ�뾲̬��Դ���ʼ��Ѿ����͡�");
        } catch (MessagingException e) {
            logger.error("����Ƕ�뾲̬��Դ���ʼ�ʱ�����쳣��", e);
        }
    }
}
