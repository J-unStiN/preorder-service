package com.junstin.preorder.config.redis;

import com.junstin.preorder.domain.email.service.EmailService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class RedisServiceTest {

    @Autowired private RedisService redisService;
    @Autowired private EmailService emailService;

    @DisplayName("레디스에 8자리 난수가 생성되는지 확인")
    @Test
    public void 레디스에난수생성확인() {
        String tempPass = emailService.generateRandomString();

        Duration duration = Duration.ofMinutes(5);
        redisService.setValues("test", tempPass, duration);
        String test = redisService.getValues("test");

        Assertions.assertThat(tempPass).isEqualTo(test);
    }

}