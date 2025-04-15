package org.example.telegramcryptobot.service.commands;

import lombok.Getter;

@Getter
public enum BotCommandType {
    HELP("/help"),
    NEWS("/news"),
    FACTS("/facts"),
    START("/start"),
    CONTENT("/content"),
    FACT_PARSING("/fact_parse"),
    SEND_FOR_ALL("/send_message"),
    SUBSCRIPTION("/get_subscription"),
    BITCOIN_PRICE("/get_price_bitcoin"),
    ETHEREUM_PRICE("/get_price_ethereum"),
    SUBSCRIBE_BITCOIN("/subscribe_bitcoin"),
    SUBSCRIBE_ETHEREUM("/subscribe_ethereum"),
    UNSUBSCRIBE_BITCOIN("/unsubscribe_bitcoin"),
    UNSUBSCRIBE_ETHEREUM("/unsubscribe_ethereum");

    private final String command;

    BotCommandType(String command) {
        this.command = command;
    }
}
