package org.example.telegram_crypto_bot.service.command.impl;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.example.telegram_crypto_bot.service.command.Commands;
import org.example.telegram_crypto_bot.service.database.UserDatabase;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class CommandForStart implements Commands {
    private final UserDatabase userDatabase;
    private static final String WELCOME_MESSAGE = EmojiParser.parseToUnicode("""
                Welcome %s!!! \uD83E\uDD73
                List of available commands:
                    /tests: "Take the tests"".
                """); // https://emojipedia.org/

    @Override
    public SendMessage getMessageResponse(Update update) {
        userDatabase.save(update);
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText(formatWelcomeMessage(update.getMessage().getChat().getUserName()));
        return message;
    }

    private String formatWelcomeMessage(String userName) {
        return String.format(WELCOME_MESSAGE, userName);
    }
}
