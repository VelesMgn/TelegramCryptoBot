package org.example.telegramcryptobot.service.command.impl.user;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.model.User;
import org.example.telegramcryptobot.repository.UserRepository;
import org.example.telegramcryptobot.service.command.impl.utils.AbstractUnsubscribeCommand;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnsubscribeBitcoinCommand extends AbstractUnsubscribeCommand {
    private final UserRepository userRepository;

    @Override
    protected void clearSubscription(User user) {
        user.setBitcoinPrice(null);
        user.setLatestBitcoinPriceNotification(null);
    }

    @Override
    protected UserRepository getUserRepository() {
        return userRepository;
    }
}