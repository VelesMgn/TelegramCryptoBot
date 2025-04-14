package org.example.telegramcryptobot.service.factory;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.commands.BotCommandType;
import org.example.telegramcryptobot.service.commands.BotCommand;
import org.example.telegramcryptobot.service.commands.impl.*;
import org.example.telegramcryptobot.service.commands.impl.SubscribeEthereumPriceCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandFactory {
    private final Map<String, BotCommand> userCommandsMap = new HashMap<>();

    private final StartCommand start;
    private final HelpCommand helpCommand;
    private final UnknownCommand unknownCommand;
    private final GetPriceBitcoinCommand priceBitcoinCommand;
    private final GetPriceEthereumCommand priceEthereumCommand;
    private final GetSubscriptionCommand getSubscriptionCommand;
    private final SubscribeBitcoinPriceCommand subscribeBitcoinCommand;
    private final SubscribeEthereumPriceCommand subscribeEthereumCommand;

    @PostConstruct
    private void init() {
        userCommandsMap.put(BotCommandType.START.getCommand(), start);
        userCommandsMap.put(BotCommandType.HELP.getCommand(), helpCommand);
        userCommandsMap.put(BotCommandType.BITCOIN_PRICE.getCommand(), priceBitcoinCommand);
        userCommandsMap.put(BotCommandType.ETHEREUM_PRICE.getCommand(), priceEthereumCommand);
        userCommandsMap.put(BotCommandType.SUBSCRIPTION.getCommand(), getSubscriptionCommand);
    }

    public BotCommand getCommand(Update update) {

        String userCommand = update.getMessage().getText();

        if(userCommand.startsWith("/subscribe_bitcoin_price")) return subscribeBitcoinCommand;
        if(userCommand.startsWith("/subscribe_ethereum_price")) return subscribeEthereumCommand;

        BotCommand command = userCommandsMap.get(userCommand);

        if(command == null) {
            command = unknownCommand;
        }

        return command;
    }
}
