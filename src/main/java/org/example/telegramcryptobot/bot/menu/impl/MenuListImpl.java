package org.example.telegramcryptobot.bot.menu.impl;

import org.example.telegramcryptobot.bot.menu.MenuList;
import org.example.telegramcryptobot.service.commands.BotCommandType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

@Component
public class MenuListImpl implements MenuList {
    @Override
    public List<BotCommand> getMenuList() {
        return List.of(new BotCommand(BotCommandType.BITCOIN_PRICE.getCommand(),
                        "Get the cost of Bitcoin in USD"),
                new BotCommand(BotCommandType.ETHEREUM_PRICE.getCommand(),
                        "Get the cost of Ethereum in USD"),
                new BotCommand(BotCommandType.HELP.getCommand(),
                        "List of available commands"));
    }
}
