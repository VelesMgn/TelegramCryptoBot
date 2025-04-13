package org.example.telegram_crypto_bot.bot.menu;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface MenuList {
    List<BotCommand> getMenuList();
}