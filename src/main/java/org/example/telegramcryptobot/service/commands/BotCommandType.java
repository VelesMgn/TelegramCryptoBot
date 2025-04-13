package org.example.telegramcryptobot.service.commands;

import lombok.Getter;

@Getter
public enum BotCommandType {
    START("/start"),
    BITCOIN_PRICE("/get_price_bitcoin"),
    ETHEREUM_PRICE("/get_price_ethereum");

    private final String command;

    BotCommandType(String command) {
        this.command = command;
    }
}
