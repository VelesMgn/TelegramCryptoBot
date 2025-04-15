package org.example.telegramcryptobot.service.command.impl.utils;

import org.example.telegramcryptobot.service.command.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractBotCommand implements BotCommand {
    protected SendMessage createMessage(Update update, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText(text);
        return message;
    }
}
