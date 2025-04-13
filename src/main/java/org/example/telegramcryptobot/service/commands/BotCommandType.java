package org.example.telegramcryptobot.service.commands;

import lombok.Getter;

@Getter
public enum BotCommandType {
    HELP("/help"),
    START("/start"),
    BITCOIN_PRICE("/get_price_bitcoin"),
    ETHEREUM_PRICE("/get_price_ethereum"),
    SUBSCRIBE_BITCOIN_PRICE("/subscribe_bitcoin_price"),
    SUBSCRIBE_ETHEREUM_PRICE("/subscribe_ethereum_price"),;

    private final String command;

    BotCommandType(String command) {
        this.command = command;
    }
}
