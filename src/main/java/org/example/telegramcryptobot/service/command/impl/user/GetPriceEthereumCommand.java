package org.example.telegramcryptobot.service.command.impl.user;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.command.BotCommand;
import org.example.telegramcryptobot.service.factory.CryptoPriceCommandFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class GetPriceEthereumCommand implements BotCommand {
    private final CryptoPriceCommandFactory factory;

    @Value("${binance.api.getPriceEthereum}")
    private String uri;

    @Override
    public SendMessage getMessageResponse(Update update) {
        return factory.createPriceMessage(update, "ethereum", uri);
    }
}
