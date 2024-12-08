package com.example.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Resource
    private JavaMailSender mailSender;

    public void sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1355252692@qq.com"); // 必须与配置的 spring.mail.username 相同
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try {
            mailSender.send(message);
            System.out.println("邮件发送成功！");
        } catch (Exception e) {
            System.err.println("邮件发送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}