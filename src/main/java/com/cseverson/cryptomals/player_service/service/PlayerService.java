package com.cseverson.cryptomals.player_service.service;

import com.cseverson.cryptomals.player_service.model.PlayerConfiguration;
import com.cseverson.cryptomals.player_service.model.PlayerRepository;
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
