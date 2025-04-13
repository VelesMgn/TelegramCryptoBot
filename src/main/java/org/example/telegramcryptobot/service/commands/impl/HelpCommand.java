package org.example.telegramcryptobot.service.commands.impl;

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
                    /subscribe_bitcoin_price [write the required price]\s
                     - subscribe to the cost of Bitcoin:\s
                     for example "/subscribe_bitcoin_price 20000"
                    /subscribe_ethereum_price [write the required price]\s
                     - subscribe to the cost of Ethereum:\s
                     for example "/subscribe_ethereum_price 1500"
                """);

    @Override
    public SendMessage getMessageResponse(Update update) {

        String username = update.getMessage().getChat().getUserName();
        String messageText = String.format(HELP_MESSAGE, username);

        return createMessage(update, messageText);
    }
}
