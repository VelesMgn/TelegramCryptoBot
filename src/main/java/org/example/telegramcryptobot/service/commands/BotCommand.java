package org.example.telegramcryptobot.service.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotCommand {
    default SendMessage getMessageResponse(Update update) {
        return null;
    }
}
