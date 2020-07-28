package com.lagou.edu.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@RefreshScope
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    /**
     * 发送验证码到邮箱，1成功，-1失败
     *
     * @param email
     * @param subject
     * @param code
     * @return
     */
    @Override
    public String sendEmail(String email, String subject, String code) {
        SimpleMailMessage message = new SimpleMailMessage();

        String text = String.format("这是你的验证码%s，十分钟内有效。%n", code);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            return "-1";
        }
        return "1";
    }
}
