package org.example.telegramcryptobot.service.factory;

import org.example.telegramcryptobot.service.commands.impl.utils.AbstractBotCommand;
import org.example.telegramcryptobot.service.factory.utils.CryptoCurrencyService;
import org.example.telegramcryptobot.utils.TextUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CryptoPriceCommandFactory extends AbstractBotCommand {
    public SendMessage createPriceMessage(Update update, String currencyName, String uri) {
        String price = TextUtil.toString(new CryptoCurrencyService(uri).getPrice());
        String messageText = String.format("Current %s price: %s USD", currencyName, price);

        return createMessage(update, messageText);
    }
}
