package org.example.telegramcryptobot.repository;

import org.example.telegramcryptobot.model.Fact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FactRepository extends CrudRepository<Fact, Long> {
}

