package com.cseverson.cryptomals.services.player;

import com.cseverson.cryptomals.model.player.PlayerConfiguration;
import com.cseverson.cryptomals.model.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.util.logging.Logger;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(PlayerConfiguration.class)
public class PlayerService {

    @Autowired
    protected PlayerRepository playerRepository;

    protected Logger log = Logger.getLogger(PlayerService.class.getName());

    public static void main(String[] args){
        System.setProperty("spring.config.name", "player-service");
        SpringApplication.run(PlayerService.class, args);
    }
}
