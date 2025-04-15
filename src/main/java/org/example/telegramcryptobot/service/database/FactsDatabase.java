package org.example.telegramcryptobot.service.database;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.model.Fact;
import org.example.telegramcryptobot.model.User;
import org.example.telegramcryptobot.repository.FactRepository;
import org.example.telegramcryptobot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactsDatabase {
    private final FactRepository factRepository;
    private final UserRepository userRepository;

    @Value("${facts.number}")
    private int numberOfFacts;

    public void save(List<Fact> factList) {
        List<Fact> newFacts = factList.stream()
                .filter(fact -> !factRepository.existsById(fact.getId()))
                .collect(Collectors.toList());

        factRepository.saveAll(newFacts);
    }

    public String getFact(Long chatId) {
//        Long id = ThreadLocalRandom.current().nextLong(1, numberOfFacts + 1); -> EditMessageText query: [400]
        final long factsIterator = 1;
        User user = userRepository.getUsersByChatId((chatId));

        if(user.getFactNumber() == null || user.getFactNumber() >= numberOfFacts + factsIterator) {
            user.setFactNumber(factsIterator);
        }

        long factNumber = user.getFactNumber();

        user.setFactNumber(user.getFactNumber() + factsIterator);
        userRepository.save(user);

        return factRepository.findById(factNumber)
                .map(Fact::getText)
                .orElse("The facts are over today.");
    }

    public void deleteAllFacts() {
        factRepository.deleteAll();
    }
}
