package org.example.telegramcryptobot.bot;

import lombok.extern.slf4j.Slf4j;
import org.example.telegramcryptobot.service.factory.CommandFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class CryptoBot extends TelegramLongPollingBot {
    private final CommandFactory commandFactory;

    @Value("${bot.name}")
    private String botName;

    public CryptoBot(@Value("${bot.token}") String botToken,
                     CommandFactory commandFactory) {
        super(botToken);
        this.commandFactory = commandFactory;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                SendMessage response = commandFactory.getCommand(update).getMessageResponse(update);
                execute(response);
            }
        } catch (TelegramApiException e) {
            log.error("Couldn't send a response: {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
