package org.example.telegramcryptobot.service.factory;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.commands.BotCommandType;
import org.example.telegramcryptobot.service.commands.BotCommand;
import org.example.telegramcryptobot.service.commands.impl.StartCommand;
import org.example.telegramcryptobot.service.commands.impl.GetPriceCommand;
import org.example.telegramcryptobot.service.commands.impl.UnknownCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandFactory {
    private final Map<String, BotCommand> userCommandsMap = new HashMap<>();

    private final UnknownCommand unknownCommand;
    private final GetPriceCommand priceCommand;
    private final StartCommand start;

    @PostConstruct
    private void init() {
        userCommandsMap.put(BotCommandType.START.getCommand(), start);
        userCommandsMap.put(BotCommandType.PRICE.getCommand(), priceCommand);
    }

    public BotCommand getCommand(Update update) {

        String userCommand = update.getMessage().getText();

        BotCommand command = userCommandsMap.get(userCommand);

        if(command == null) {
            command = unknownCommand;
        }

        return command;
    }
}
