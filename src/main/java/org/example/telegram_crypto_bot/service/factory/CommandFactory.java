package org.example.telegram_crypto_bot.service.factory;

import lombok.RequiredArgsConstructor;
import org.example.telegram_crypto_bot.service.command.ListingBotCommands;
import org.example.telegram_crypto_bot.service.command.Commands;
import org.example.telegram_crypto_bot.service.command.impl.CommandForStart;
import org.example.telegram_crypto_bot.service.command.impl.UnknownCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandFactory {
    private final Map<String, Commands> USER_COMMANDS_MAP = new HashMap<>();

    private final UnknownCommand unknownCommand;
    private final CommandForStart start;

    public Commands getCommand(Update update) {
        addCommand();

        String userCommand = update.getMessage().getText();

        Commands command = USER_COMMANDS_MAP.get(userCommand);

        if(command == null) {
            command = unknownCommand;
        }

        return command;
    }

    private void addCommand() {
        USER_COMMANDS_MAP.put(ListingBotCommands.START.getCommand(), start);
    }
}
