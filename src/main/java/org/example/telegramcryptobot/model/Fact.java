package org.example.telegramcryptobot.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fact", indexes = @Index(name = "fact_index", columnList = "id"))
public class Fact {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "text_fact", columnDefinition = "TEXT")
    private String text;
}
