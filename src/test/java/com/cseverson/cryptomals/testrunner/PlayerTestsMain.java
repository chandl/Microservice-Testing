package com.cseverson.cryptomals.testrunner;

import com.cseverson.cryptomals.model.player.PlayerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Simulate the {@link com.cseverson.cryptomals.services.player.PlayerService}, without using any of the discovery client
 * code. Allows the test to use the same configuration as the PlayerService would.
 *
 * @author Chandler Severson
 */
@SpringBootApplication
@Import(PlayerConfiguration.class)
public class PlayerTestsMain {
    public static void main(String[] args){
        System.setProperty("spring.config.name", "web-player-server");
        SpringApplication.run(PlayerTestsMain.class, args);
    }
}
