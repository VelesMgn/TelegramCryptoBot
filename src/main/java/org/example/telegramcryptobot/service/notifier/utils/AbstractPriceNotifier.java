package org.example.telegramcryptobot.service.notifier.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.telegramcryptobot.model.User;
import org.example.telegramcryptobot.service.factory.utils.CryptoCurrencyService;
import org.example.telegramcryptobot.utils.TextUtil;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractPriceNotifier {
    private List<SendMessage> notificationList;

    @Value("${bot.notify.delay.value}")
    private long notifyDelayValue;

    @Value("${bot.notify.delay.unit}")
    private String notifyDelayUnit;

    protected abstract List<User> getUsers(double currentPrice);
    protected abstract Timestamp getNotificationTime(User user);
    protected abstract void saveUsers(User user, Instant now);
    protected abstract String getNotificationText();
    protected abstract String getUri();

    public List<SendMessage> getUserNotifications() {
        notificationList = new ArrayList<>();
        double currentPrice = new CryptoCurrencyService(getUri()).getPrice();

        log.info("The current price: {}", currentPrice);

        List<User> users = getUsers(currentPrice);
        Instant now = Instant.now();
        Duration notifyDelay = parseNotifyDelay();

        for (User user : users) {
            if (getNotificationTime(user) != null) {
                Duration sinceLast = Duration.between(getNotificationTime(user).toInstant(), now);
                if (sinceLast.compareTo(notifyDelay) < 0) {
                    continue;
                }
            }

            sendMessage(user, currentPrice);
            saveUsers(user, now);
        }

        return notificationList;
    }

    private void sendMessage(User user, double currentPrice) {
        String textMessage = String.format(getNotificationText(), TextUtil.toString(currentPrice));

        SendMessage message = new SendMessage();
        message.setChatId(user.getChatId());
        message.setText(textMessage);

        notificationList.add(message);
    }

    private Duration parseNotifyDelay() {
        return switch (notifyDelayUnit.toUpperCase()) {
            case "SECONDS" -> Duration.ofSeconds(notifyDelayValue);
            case "MINUTES" -> Duration.ofMinutes(notifyDelayValue);
            case "HOURS" -> Duration.ofHours(notifyDelayValue);
            default -> throw new IllegalArgumentException("Unknown time unit: " + notifyDelayUnit);
        };
    }
}
