package com.lagou.edu.service.impl;

import com.lagou.edu.dao.CodeDao;
import com.lagou.edu.pojo.LagouCode;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Service
public class CodeServiceImpl implements CodeService {

    @Reference(url = "dubbo://127.0.0.1:20880?timeout=3000", version = "1.0")
    private EmailService emailService;

    @Autowired
    private CodeDao codeDao;

    // 生成验证码入库并发送到对应邮箱，成功true，失败false
    public String createCode(String email) {
        String randomCode = randomCode();
        LagouCode lagouCode = new LagouCode();
        lagouCode.setCode(randomCode);
        lagouCode.setEmail(email);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime after10Mins = now.plus(Duration.of(10, ChronoUnit.MINUTES));
        lagouCode.setCreateTime(now);
        lagouCode.setExpireTime(after10Mins);
        codeDao.save(lagouCode);
        return emailService.sendEmail(email, "验证码", randomCode);
    }

    @Override
    // 校验验证码是否正确，0正确1错误2超时
    public String validateCode(String email, String code) {
        List<LagouCode> lagouCodes = codeDao.findByEmailOrderByCreateTimeDesc(email);
        LagouCode latest = lagouCodes.get(0);
        if (latest != null && latest.getCode() != null && latest.getCode().equals(code)) {
            return LocalDateTime.now().isBefore(latest.getExpireTime()) ? "0" : "2";
        }
        return "1";
    }

    private static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
