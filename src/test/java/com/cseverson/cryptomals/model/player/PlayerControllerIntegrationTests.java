package com.cseverson.cryptomals.model.player;


import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootApplication
@Import(PlayerConfiguration.class)
class PlayersMain {
    public static void main(String[] args){

        System.setProperty("spring.config.name", "player-server");
        SpringApplication.run(PlayersMain.class, args);
    }
}


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PlayersMain.class)
public class PlayerControllerIntegrationTests extends AbstractPlayerControllerTests{
}
