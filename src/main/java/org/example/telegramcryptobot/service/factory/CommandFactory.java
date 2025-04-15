package org.example.telegramcryptobot.service.factory;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.command.BotCommandType;
import org.example.telegramcryptobot.service.command.BotCommand;
import org.example.telegramcryptobot.service.command.impl.admin.FactParsingCommand;
import org.example.telegramcryptobot.service.command.impl.admin.MessageForAllCommand;
import org.example.telegramcryptobot.service.command.impl.user.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandFactory {
    private final Map<String, BotCommand> adminCommandsMap = new HashMap<>();
    private final Map<String, BotCommand> userCommandsMap = new HashMap<>();
    private final Map<String, BotCommand> callbackMap = new HashMap<>();

    private final SubscribeEthereumPriceCommand subscribeEthereumCommand;
    private final UnsubscribeEthereumCommand unsubscribeEthereumCommand;
    private final SubscribeBitcoinPriceCommand subscribeBitcoinCommand;
    private final UnsubscribeBitcoinCommand unsubscribeBitcoinCommand;
    private final GetSubscriptionCommand getSubscriptionCommand;
    private final GetPriceEthereumCommand priceEthereumCommand;
    private final GetPriceBitcoinCommand priceBitcoinCommand;
    private final FactParsingCommand factParsingCommand;
    private final GetContentCommand getContentCommand;
    private final GetNewsCommand getNewsCommand;
    private final GetFactCommand getFactCommand;
    private final UnknownCommand unknownCommand;
    private final HelpCommand helpCommand;
    private final StartCommand start;

    private final MessageForAllCommand messageForAllCommand;

    @Value("${admin.id}")
    private int adminId;

    @PostConstruct
    private void userCommandsInit() {
        userCommandsMap.put(BotCommandType.UNSUBSCRIBE_ETHEREUM.getCommand(), unsubscribeEthereumCommand);
        userCommandsMap.put(BotCommandType.UNSUBSCRIBE_BITCOIN.getCommand(), unsubscribeBitcoinCommand);
        userCommandsMap.put(BotCommandType.SUBSCRIPTION.getCommand(), getSubscriptionCommand);
        userCommandsMap.put(BotCommandType.ETHEREUM_PRICE.getCommand(), priceEthereumCommand);
        userCommandsMap.put(BotCommandType.BITCOIN_PRICE.getCommand(), priceBitcoinCommand);
        userCommandsMap.put(BotCommandType.CONTENT.getCommand(), getContentCommand);
        userCommandsMap.put(BotCommandType.FACTS.getCommand(), getFactCommand);
        userCommandsMap.put(BotCommandType.NEWS.getCommand(), getNewsCommand);
        userCommandsMap.put(BotCommandType.HELP.getCommand(), helpCommand);
        userCommandsMap.put(BotCommandType.START.getCommand(), start);
    }

    @PostConstruct
    private void adminCommandsInit() {
        adminCommandsMap.put(BotCommandType.FACT_PARSING.getCommand(), factParsingCommand);
    }

    @PostConstruct
    private void callbackCommandInit() {
        callbackMap.put(BotCommandType.YES.getCommand(), getFactCommand);
        callbackMap.put(BotCommandType.NO.getCommand(), getFactCommand);
    }

    public BotCommand getCommand(Update update) {
        final String userCommand = update.getMessage().getText();
        final Long chatId = update.getMessage().getChatId();

        if(userCommand.startsWith(BotCommandType.SUBSCRIBE_BITCOIN.getCommand())) return subscribeBitcoinCommand;
        if(userCommand.startsWith(BotCommandType.SUBSCRIBE_ETHEREUM.getCommand())) return subscribeEthereumCommand;
        if(userCommand.startsWith(BotCommandType.SEND_FOR_ALL.getCommand())
                && adminId == chatId) return messageForAllCommand;

        BotCommand command = userCommandsMap.get(userCommand);

        if(command == null) command = adminCommandsMap.get(userCommand);
        if(command == null || adminId != chatId) command = unknownCommand;

        return command;
    }

    public BotCommand getCallback(Update update) {
        String callbackData = update.getCallbackQuery().getData();

        return callbackMap.get(callbackData);
    }
}
