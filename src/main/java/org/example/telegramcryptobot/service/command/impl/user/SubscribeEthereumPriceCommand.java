package org.example.telegramcryptobot.service.command.impl.user;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.command.BotCommandType;
import org.example.telegramcryptobot.service.command.impl.utils.AbstractSubscribeCryptoPriceCommand;
import org.example.telegramcryptobot.service.database.UserDatabase;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class SubscribeEthereumPriceCommand extends AbstractSubscribeCryptoPriceCommand {
    private final GetPriceEthereumCommand getPriceEthereumCommand;
    private final UserDatabase database;

    @Override
    protected Integer getCommandPrefixLength() {
        return BotCommandType.SUBSCRIBE_ETHEREUM.getCommand().length();
    }

    @Override
    protected String getCryptoPrice(Update update) {
        return getPriceEthereumCommand.getMessageResponse(update).getText();
    }

    @Override
    protected void updateUserData(Double price, Update update) {
        database.updateEthereumPrice(update.getMessage().getChatId(), price);
    }
}
