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

    @Column(name = "price")
    private Double price;
}
