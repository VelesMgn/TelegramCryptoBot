package org.example.telegramcryptobot.service.commands;

import lombok.Getter;

@Getter
public enum BotCommandType {
    HELP("/help"),
    START("/start"),
    SUBSCRIPTION("/get_subscription"),
    BITCOIN_PRICE("/get_price_bitcoin"),
    ETHEREUM_PRICE("/get_price_ethereum"),
    UNSUBSCRIBE_BITCOIN("/unsubscribe_bitcoin"),
    UNSUBSCRIBE_ETHEREUM("/unsubscribe_ethereum"),
    SUBSCRIBE_BITCOIN_PRICE("/subscribe_bitcoin"),
    SUBSCRIBE_ETHEREUM_PRICE("/subscribe_ethereum"),;

    private final String command;

    BotCommandType(String command) {
        this.command = command;
    }
}
