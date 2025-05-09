package org.example.telegramcryptobot.service.notifier;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.model.User;
import org.example.telegramcryptobot.repository.UserRepository;
import org.example.telegramcryptobot.service.notifier.utils.AbstractPriceNotifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BitcoinPriceNotifier extends AbstractPriceNotifier {
    private final static String NOTIFICATION_TEXT = "It's time to buy, the cost of Bitcoin: %s USD";
    private final UserRepository userRepository;

    @Value("${binance.api.getPriceBitcoin}")
    private String uri;

    @Override
    protected List<User> getUsers(double currentPrice) {
        return userRepository.findAllByBitcoinPriceGreaterThan(currentPrice);
    }

    @Override
    protected Timestamp getNotificationTime(User user) {
        return user.getLatestBitcoinPriceNotification();
    }

    @Override
    protected void saveUsers(User user, Instant now) {
        user.setLatestBitcoinPriceNotification(Timestamp.from(now));
        userRepository.save(user);
    }

    @Override
    protected String getNotificationText() {
        return NOTIFICATION_TEXT;
    }

    @Override
    protected String getUri() {
        return uri;
    }
}
