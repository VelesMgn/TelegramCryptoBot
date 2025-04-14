package org.example.telegramcryptobot.service.commands.impl;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.commands.impl.utils.AbstractBotCommand;
import org.example.telegramcryptobot.service.database.UserDatabase;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class StartCommand extends AbstractBotCommand {
    private static final String WELCOME_MESSAGE = EmojiParser.parseToUnicode("""
                Welcome %s!!! \uD83E\uDD73
                List of available commands:
                    /help - shows all available commands,
                    /get_price_bitcoin - get the cost of Bitcoin in USD,
                    /get_price_ethereum - get the cost of Ethereum in USD,
                    /get_subscription - get current subscriptions,
                    /subscribe_bitcoin [write the required price]
                     - subscribe to the cost of Bitcoin:
                    /subscribe_ethereum [write the required price]
                     - subscribe to the cost of Ethereum:
                """); // https://emojipedia.org/

    private final UserDatabase userDatabase;

    @Override
    public SendMessage getMessageResponse(Update update) {
        userDatabase.save(update);

        String username = update.getMessage().getChat().getUserName();
        String messageText = String.format(WELCOME_MESSAGE, username);

        return createMessage(update, messageText);
    }
}
