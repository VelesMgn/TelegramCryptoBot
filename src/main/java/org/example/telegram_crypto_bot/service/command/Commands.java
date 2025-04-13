package org.example.telegram_crypto_bot.service.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Commands {
    default SendMessage getMessageResponse(Update update) {
        return null;
    }
}
