package org.example.telegramcryptobot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.commands.BotCommand;
import org.example.telegramcryptobot.service.crypto.CryptoCurrencyService;
import org.example.telegramcryptobot.utils.TextUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class GetPriceCommand implements BotCommand {
    private final CryptoCurrencyService service;

    @Override
    public SendMessage getMessageResponse(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText("Current Bitcoin Price " + TextUtil.toString(service.getBitcoinPrice()) + " USD");
        return message;
    }
}
