package org.example.telegramcryptobot.service.crypto;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CryptoCurrencyService {
    private final AtomicReference<Double> price = new AtomicReference<>();
    private final BinanceClient client;

    public CryptoCurrencyService(BinanceClient client) {
        this.client = client;
    }

    public double getBitcoinPrice() {
        try {
            price.set(client.getBitcoinPrice());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return price.get();
    }
}
