package org.example.telegramcryptobot.bot;

import lombok.extern.slf4j.Slf4j;
import org.example.telegramcryptobot.service.commands.BotCommand;
import org.example.telegramcryptobot.service.commands.impl.admin.MessageForAllCommand;
import org.example.telegramcryptobot.service.commands.impl.user.UnknownCommand;
import org.example.telegramcryptobot.service.factory.CommandFactory;
import org.example.telegramcryptobot.service.notifier.BitcoinPriceNotifier;
import org.example.telegramcryptobot.service.notifier.EthereumPriceNotifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Component
public class CryptoBot extends TelegramLongPollingBot {
    private final static String ERROR_MESSAGE = "Couldn't send a response: {}";

    private final CommandFactory commandFactory;
    private final BitcoinPriceNotifier bitcoinPriceNotifier;
    private final EthereumPriceNotifier ethereumPriceNotifier;

    @Value("${bot.name}")
    private String botName;

    public CryptoBot(@Value("${bot.token}") String botToken,
                     CommandFactory commandFactory,
                     BitcoinPriceNotifier priceNotifier,
                     EthereumPriceNotifier ethereumPriceNotifier) {
        super(botToken);
        this.commandFactory = commandFactory;
        this.bitcoinPriceNotifier = priceNotifier;
        this.ethereumPriceNotifier = ethereumPriceNotifier;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                BotCommand command = commandFactory.getCommand(update);

                if (command instanceof MessageForAllCommand) {
                    List<SendMessage> messageForAll = command.getMessageForAll(update);
                    for (SendMessage message : messageForAll) execute(message);
                } else {
                    SendMessage response = command.getMessageResponse(update);
                    execute(response);
                }

            }

        } catch (TelegramApiException e) {
            log.error(ERROR_MESSAGE, String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    private void processNotifications(Supplier<List<SendMessage>> notificationSupplier) {
        try {
            List<SendMessage> notifications = notificationSupplier.get();
            if (notifications != null && !notifications.isEmpty()) {
                for (SendMessage message : notifications) {
                    execute(message);
                }
            }
        } catch (TelegramApiException e) {
            log.error(ERROR_MESSAGE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedRateString = "${bot.price-check.interval-ms}")
    private void runNotifications() {
        processNotifications(bitcoinPriceNotifier::getUserNotifications);
        processNotifications(ethereumPriceNotifier::getUserNotifications);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
