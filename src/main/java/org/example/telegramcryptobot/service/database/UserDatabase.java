package org.example.telegramcryptobot.service.database;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.model.User;
import org.example.telegramcryptobot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDatabase {
    private final UserRepository userRepository;

    public void save(Update update) {
        if(!userRepository.existsById(update.getMessage().getChatId())) {
            User user = User.builder()
                    .chatId(update.getMessage().getChatId())
                    .userName(update.getMessage().getChat().getUserName())
                    .build();

            userRepository.save(user);
        }
    }

    public void updateBitcoinPrice(Long chatId, Double price) {
        Optional<User> optionalUser = userRepository.findById(chatId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setBitcoinPrice(price);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with chatId = " + chatId);
        }
    }

    public void updateEthereumPrice(Long chatId, Double price) {
        Optional<User> optionalUser = userRepository.findById(chatId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEthereumPrice(price);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with chatId = " + chatId);
        }
    }
}
