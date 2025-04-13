package org.example.telegram_crypto_bot.service.command;

import lombok.Getter;

@Getter
public enum ListingBotCommands {
    START("/start");

    private final String command;

    ListingBotCommands(String command) {
        this.command = command;
    }
}
