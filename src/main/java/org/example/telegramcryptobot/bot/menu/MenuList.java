package org.example.telegramcryptobot.bot.menu;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface MenuList {
    List<BotCommand> getMenuList();
}