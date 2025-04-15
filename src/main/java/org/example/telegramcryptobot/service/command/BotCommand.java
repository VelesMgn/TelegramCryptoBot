package org.example.telegramcryptobot.service.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface BotCommand {
    default SendMessage getMessageResponse(Update update) {
        return null;
    }

    default List<SendMessage> getMessageForAll(Update update) {
        return null;
    }

    default EditMessageText getCallbackAnswer(Update update) {
        return null;
    }
}
