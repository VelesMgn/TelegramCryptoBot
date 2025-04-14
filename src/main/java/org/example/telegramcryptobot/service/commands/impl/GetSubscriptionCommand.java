package org.example.telegramcryptobot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.repository.UserRepository;
import org.example.telegramcryptobot.service.commands.impl.utils.AbstractBotCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class GetSubscriptionCommand extends AbstractBotCommand {
    private final UserRepository userRepository;
    private StringBuilder builder;

    @Override
    public SendMessage getMessageResponse(Update update) {
        Long chatId = update.getMessage().getChatId();
        return userRepository.findById(chatId)
                .map(user -> {
                    Double bitcoinPrice = user.getBitcoinPrice();
                    Double ethereumPrice = user.getEthereumPrice();

                    if (bitcoinPrice == null && ethereumPrice == null) {
                        return createMessage(update, "There are no active subscriptions");
                    }

                    builder = new StringBuilder("Your current subscriptions:\n");

                    if (bitcoinPrice != null) {
                        builder.append(" - Bitcoin: ").append(bitcoinPrice).append("\n");
                    }

                    if (ethereumPrice != null) {
                        builder.append(" - Ethereum: ").append(ethereumPrice);
                    }

                    return createMessage(update, builder.toString());
                })
                .orElseGet(() -> createMessage(update, "User is not registered, enter command /start"));
    }
}
