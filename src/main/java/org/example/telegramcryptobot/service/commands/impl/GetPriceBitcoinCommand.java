package org.example.telegramcryptobot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.commands.BotCommand;
import org.example.telegramcryptobot.service.factory.CryptoPriceCommandFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class GetPriceBitcoinCommand implements BotCommand {
    private final CryptoPriceCommandFactory factory;

    @Value("${binance.api.getPriceBitcoin}")
    private String uri;

    @Override
    public SendMessage getMessageResponse(Update update) {
        return factory.createPriceMessage(update, "bitcoin", uri);
    }
}
