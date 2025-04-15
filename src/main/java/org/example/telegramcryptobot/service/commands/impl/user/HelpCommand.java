package org.example.telegramcryptobot.service.commands.impl.user;

import com.vdurmont.emoji.EmojiParser;
import org.example.telegramcryptobot.service.commands.impl.utils.AbstractBotCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class HelpCommand extends AbstractBotCommand {
    private static final String HELP_MESSAGE = EmojiParser.parseToUnicode("""
                List of available commands:
                    /help - shows all available commands,
                    /get_price_bitcoin - get the cost of Bitcoin in USD,
                    /get_price_ethereum - get the cost of Ethereum in USD,
                    /get_subscription - get current subscriptions,
                    /subscribe_bitcoin [write the required price]
                     - subscribe to the cost of Bitcoin,
                    /subscribe_ethereum [write the required price]
                     - subscribe to the cost of Ethereum,
                    /content - educational content: news and facts.
                """);

    @Override
    public SendMessage getMessageResponse(Update update) {

        String username = update.getMessage().getChat().getUserName();
        String messageText = String.format(HELP_MESSAGE, username);

        return createMessage(update, messageText);
    }
}
