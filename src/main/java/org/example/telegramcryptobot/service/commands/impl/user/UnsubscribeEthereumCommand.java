package org.example.telegramcryptobot.service.commands.impl.user;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.model.User;
import org.example.telegramcryptobot.repository.UserRepository;
import org.example.telegramcryptobot.service.commands.impl.utils.AbstractUnsubscribeCommand;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnsubscribeEthereumCommand extends AbstractUnsubscribeCommand {
    private final UserRepository userRepository;

    @Override
    protected void clearSubscription(User user) {
        user.setEthereumPrice(null);
        user.setLatestEthereumPriceNotification(null);
    }

    @Override
    protected UserRepository getUserRepository() {
        return userRepository;
    }
}
