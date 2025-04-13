package org.example.telegram_crypto_bot.service.command.impl;

import lombok.RequiredArgsConstructor;
import org.example.telegram_crypto_bot.service.command.Commands;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class UnknownCommand implements Commands {
    private static final String UNKNOWN_COMMAND = "The bot does not know such a command: %s";

    @Override
    public SendMessage getMessageResponse(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText(String.format(UNKNOWN_COMMAND, update.getMessage().getText()));

        return message;
    }
}
