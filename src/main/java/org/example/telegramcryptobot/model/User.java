package org.example.telegramcryptobot.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`user`", indexes = @Index(name = "user_index", columnList = "chat_id"))
public class User {
    @Id
    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "bitcoin_notification")
    private Timestamp latestBitcoinPriceNotification;

    @Column(name = "ethereum_notification")
    private Timestamp latestEthereumPriceNotification;

    @Column(name = "bitcoin_price")
    private Double bitcoinPrice;

    @Column(name = "ethereum_price")
    private Double ethereumPrice;

    @Column(name = "fact_number")
    private Long factNumber;
}
