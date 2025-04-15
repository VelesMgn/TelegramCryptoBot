package org.example.telegramcryptobot.repository;

import org.example.telegramcryptobot.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    @Query(
            value = "SELECT * FROM \"user\" WHERE bitcoin_price >= :currentPrice",
            nativeQuery = true
    )
    List<User> findAllByBitcoinPriceGreaterThan(@Param("currentPrice") Double currentPrice);

    @Query(
            value = "SELECT * FROM \"user\" WHERE ethereum_price >= :currentPrice",
            nativeQuery = true
    )
    List<User> findAllByEthereumPriceGreaterThan(@Param("currentPrice") Double currentPrice);

    User getUsersByChatId(Long chatId);
}