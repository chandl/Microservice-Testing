package com.cseverson.cryptomals.model.player;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PlayerRepository extends Repository<Player, Long> {

    public Player findById(Long id);

    public List<Player> findByUserNameIgnoreCase(String user_name);

    @Query("SELECT count(*) from Player")
    public int countAccounts();
}
