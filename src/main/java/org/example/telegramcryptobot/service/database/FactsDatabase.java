package org.example.telegramcryptobot.service.database;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.model.Fact;
import org.example.telegramcryptobot.repository.FactRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactsDatabase {
    private final FactRepository factRepository;
    @Value("${facts.number}")
    private int numberOfFacts;

    public void save(List<Fact> factList) {
        List<Fact> newFacts = factList.stream()
                .filter(fact -> !factRepository.existsById(fact.getId()))
                .collect(Collectors.toList());

        factRepository.saveAll(newFacts);
    }

    public String getFact() {
        Long id = ThreadLocalRandom.current().nextLong(1, numberOfFacts + 1);
        return factRepository.findById(id)
                .map(Fact::getText)
                .orElse("The facts are over today.");
    }

    public void deleteAllFacts() {
        factRepository.deleteAll();
    }
}
