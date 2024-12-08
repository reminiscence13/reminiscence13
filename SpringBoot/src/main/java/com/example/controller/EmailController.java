package com.example.controller;

import com.example.service.EmailService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    @Resource
    private EmailService emailService;

    @GetMapping("/order/sendEmail")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        emailService.sendSimpleMail(to, subject, text);
//        System.out.println(to);
//        System.out.println(subject);
//        System.out.println(text);
        return "邮件已发送！";
    }
}