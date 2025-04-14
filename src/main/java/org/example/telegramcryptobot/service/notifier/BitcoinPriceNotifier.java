package org.example.telegramcryptobot.service.notifier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.telegramcryptobot.model.User;
import org.example.telegramcryptobot.repository.UserRepository;
import org.example.telegramcryptobot.service.factory.utils.CryptoCurrencyService;
import org.example.telegramcryptobot.utils.TextUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BitcoinPriceNotifier {
    private final static String notificationText = "It's time to buy, the cost of Bitcoin: %s USD";
    private final UserRepository userRepository;

    private List<SendMessage> notificationList;

    @Value("${bot.notify.delay.value}")
    private long notifyDelayValue;

    @Value("${bot.notify.delay.unit}")
    private String notifyDelayUnit;

    @Value("${binance.api.getPriceBitcoin}")
    private String uri;

    public List<SendMessage> getUserNotifications() {
        notificationList = new ArrayList<>();
        double currentPrice = new CryptoCurrencyService(uri).getPrice();

        log.info("The current price of Bitcoin: {}", currentPrice);

        List<User> users = userRepository.findAllByBitcoinPriceGreaterThan(currentPrice);

        Instant now = Instant.now();
        Duration notifyDelay = parseNotifyDelay();

        for (User user : users) {
            if (user.getLastNotificationTime() != null) {
                Duration sinceLast = Duration.between(user.getLastNotificationTime().toInstant(), now);
                if (sinceLast.compareTo(notifyDelay) < 0) {
                    continue;
                }
            }

            sendMessage(user, currentPrice);

            user.setLastNotificationTime(Timestamp.from(now));
            userRepository.save(user);
        }

        return notificationList;
    }

    private void sendMessage(User user, double currentPrice) {
        String textMessage = String.format(notificationText, TextUtil.toString(currentPrice));

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
