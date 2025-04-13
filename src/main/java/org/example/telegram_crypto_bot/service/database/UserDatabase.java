package org.example.telegram_crypto_bot.service.database;

import lombok.RequiredArgsConstructor;
import org.example.telegram_crypto_bot.model.User;
import org.example.telegram_crypto_bot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDatabase {
    private final UserRepository userRepository;

    public void save(Update update) {
        if(!userRepository.existsById(update.getMessage().getChatId())) {
            User user = User.builder()
                    .chatId(update.getMessage().getChatId())
                    .userName(update.getMessage().getChat().getUserName())
                    .registeredAt(new Timestamp(System.currentTimeMillis()))
                    .build();

            userRepository.save(user);
        }
    }
}
