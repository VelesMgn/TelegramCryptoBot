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
@Table(name = "`user`")
public class User {
    @Id
    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "registered_at", nullable = false)
    private Timestamp registeredAt;

    @Column(name = "bitcoin_price")
    private Double bitcoinPrice;

    @Column(name = "ethereum_price")
    private Double ethereumPrice;
}
