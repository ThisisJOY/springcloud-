package com.lagou.edu.service.impl;

public interface EmailService {

    String sendEmail(String email, String subject, String code);
}
