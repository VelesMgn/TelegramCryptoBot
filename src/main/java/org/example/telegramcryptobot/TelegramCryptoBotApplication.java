package org.example.telegramcryptobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TelegramCryptoBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramCryptoBotApplication.class, args);
    }
}
