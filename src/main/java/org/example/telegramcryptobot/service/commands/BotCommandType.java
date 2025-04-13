package org.example.telegramcryptobot.service.commands;

import lombok.Getter;

@Getter
public enum BotCommandType {
    START("/start"),
    PRICE("/get_price");

    private final String command;

    BotCommandType(String command) {
        this.command = command;
    }
}
