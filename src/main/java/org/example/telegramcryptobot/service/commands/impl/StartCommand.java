package org.example.telegramcryptobot.service.commands.impl;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.commands.BotCommand;
import org.example.telegramcryptobot.service.database.UserDatabase;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class StartCommand implements BotCommand {
    private final UserDatabase userDatabase;
    private static final String WELCOME_MESSAGE = EmojiParser.parseToUnicode("""
                Welcome %s!!! \uD83E\uDD73
                List of available commands:
                    /get_price - get the cost of Bitcoin in USD.
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
