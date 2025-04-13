package org.example.telegramcryptobot.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.telegramcryptobot.bot.CryptoBot;
import org.example.telegramcryptobot.bot.menu.MenuList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BotInitializer {
    private final MenuList menuList;
    private final CryptoBot bot;

    @Bean
    public TelegramBotsApi telegramBotsApi(){
        TelegramBotsApi api;
        try {
            api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);
        } catch (TelegramApiException e) {
            log.error("Error during bot initialization: {}", e.toString());
            throw new RuntimeException(e);
        }
        return api;
    }

    @PostConstruct
    private void registeringCommandMenu() {
        try {
            bot.execute(new SetMyCommands(menuList.getMenuList(), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Menu initialization error: {}", e.toString());
            throw new RuntimeException(e);
        }
    }
}
