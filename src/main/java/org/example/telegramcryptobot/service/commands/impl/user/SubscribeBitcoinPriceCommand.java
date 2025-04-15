package org.example.telegramcryptobot.service.commands.impl.user;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.commands.BotCommandType;
import org.example.telegramcryptobot.service.commands.impl.utils.AbstractSubscribeCryptoPriceCommand;
import org.example.telegramcryptobot.service.database.UserDatabase;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class SubscribeBitcoinPriceCommand extends AbstractSubscribeCryptoPriceCommand {
    private final GetPriceBitcoinCommand getPriceBitcoinCommand;
    private final UserDatabase database;

    @Override
    protected Integer getCommandPrefixLength() {
         return BotCommandType.SUBSCRIBE_BITCOIN.getCommand().length();
    }

    @Override
    protected String getCryptoPrice(Update update) {
        return getPriceBitcoinCommand.getMessageResponse(update).getText();
    }

    @Override
    protected void updateUserData(Double price, Update update) {
        database.updateBitcoinPrice(update.getMessage().getChatId(), price);
    }
}
