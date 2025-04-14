package org.example.telegramcryptobot.service.factory.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class CryptoCurrencyService {
    private final AtomicReference<Double> price = new AtomicReference<>();
    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final HttpGet httpGet;

    public CryptoCurrencyService(String uri) {
        httpGet = new HttpGet(uri);
        mapper = new ObjectMapper();
        httpClient = HttpClientBuilder.create()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
    }

    @SneakyThrows
    public double getPrice() {
        try {
            double bitcoinPrice = mapper.readTree(EntityUtils.toString(httpClient.execute(httpGet).getEntity()))
                    .path("price").asDouble();

            price.set(bitcoinPrice);
            return price.get();
        } catch (IOException e) {
            log.info("Couldn't get the Bitcoin price: {}", e.getMessage());
            throw e;
        }
    }
}
