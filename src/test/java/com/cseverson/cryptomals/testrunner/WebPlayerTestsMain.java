package com.cseverson.cryptomals.testrunner;

import com.cseverson.cryptomals.services.web.player.WebPlayerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Simulate the {@link WebPlayerService}, without using any of the discovery client
 * code. Allows the test to use the same configuration as the PlayerService would.
 *
 * @author Chandler Severson
 */
@SpringBootApplication
public class WebPlayerTestsMain {
    public static void main(String[] args){
        System.setProperty("spring.config.name", "player-server");
        SpringApplication.run(WebPlayerTestsMain.class, args);
    }
}