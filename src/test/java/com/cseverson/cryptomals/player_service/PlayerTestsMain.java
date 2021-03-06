package com.cseverson.cryptomals.player_service;

import com.cseverson.cryptomals.player_service.model.PlayerConfiguration;
import com.cseverson.cryptomals.player_service.service.PlayerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Simulate the {@link PlayerService}, without using any of the discovery client
 * code. Allows the test to use the same configuration as the PlayerService would.
 *
 * @author Chandler Severson
 */
@SpringBootApplication
@Import(PlayerConfiguration.class)
public class PlayerTestsMain {
    public static void main(String[] args){
        System.setProperty("spring.config.name", "player-service");
        SpringApplication.run(PlayerTestsMain.class, args);
    }
}
