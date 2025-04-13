package org.example.telegram_crypto_bot.bot.menu.impl;

import org.example.telegram_crypto_bot.bot.menu.MenuList;
import org.example.telegram_crypto_bot.service.command.ListingBotCommands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

@Component
public class MenuListImpl implements MenuList {
    @Override
    public List<BotCommand> getMenuList() {
        return List.of(new BotCommand(ListingBotCommands.START.getCommand(), "start"));
    }
}
