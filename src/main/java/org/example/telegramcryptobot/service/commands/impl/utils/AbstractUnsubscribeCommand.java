package org.example.telegramcryptobot.service.commands.impl.utils;

import org.example.telegramcryptobot.model.User;
import org.example.telegramcryptobot.repository.UserRepository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractUnsubscribeCommand extends AbstractBotCommand {
    private final static String ANSWER = "Subscription cancelled";

    protected abstract void clearSubscription(User user);
    protected abstract UserRepository getUserRepository();

    @Override
    public SendMessage getMessageResponse(Update update) {
        Long chatId = update.getMessage().getChatId();
        User user = getUserRepository().findById(chatId).orElse(null);
        if (user != null) {
            clearSubscription(user);
            getUserRepository().save(user);
        }

        return createMessage(update, ANSWER);
    }
}
