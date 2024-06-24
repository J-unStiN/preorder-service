package com.junstin.preorder.domain.email.service;

import com.junstin.preorder.config.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final RedisService redisService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void emailAuthSend(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(fromEmail);
        mailSender.send(message);
    }


    /* 레디스에 저장된 인증코드 검증 */
    public Boolean isRandomString(String email, String randomString) {
        String values = redisService.getValues(email);
        if (Objects.equals(values, "false")) {
            return false;
        }

        if(!values.equals(randomString)) {
            return false;
        }

        return true;
    }


    /* 8자리 난수 생성 */
    public String generateRandomString() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int LENGTH = 8;
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }


}
