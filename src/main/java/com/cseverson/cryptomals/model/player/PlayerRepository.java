package com.cseverson.cryptomals.model.player;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Repository for Player data implemented using Spring Data JPA.
 *
 * @author Chandler Severson
 */
public interface PlayerRepository extends Repository<Player, Long> {

    Player save(Player player);

    void delete(Player player);

    Iterable<Player> findAll();

    Player findById(Long id);

    List<Player> findByUserNameContainingIgnoreCase(String user_name);

    @Query("SELECT count(*) from Player")
    int countAccounts();
}
