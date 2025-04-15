package org.example.telegramcryptobot.service.command.impl.user;

import org.example.telegramcryptobot.service.command.impl.utils.AbstractBotCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UnknownCommand extends AbstractBotCommand {
    private static final String UNKNOWN_COMMAND = "The bot does not know such a command: %s";

    @Override
    public SendMessage getMessageResponse(Update update) {
        String messageText = String.format(UNKNOWN_COMMAND, update.getMessage().getText());
        return createMessage(update, messageText);
    }
}
