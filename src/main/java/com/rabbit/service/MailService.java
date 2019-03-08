package com.rabbit.service;

public interface MailService {
    /**
     * ������ͨ�ı�
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String subject, String content);

    /**
     * ���� html ������ʼ�
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String to, String subject, String content);

    /**
     * ���͸������ʼ�
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * ������Ƕ���ļ�
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
}
