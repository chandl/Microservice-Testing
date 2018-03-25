package com.cseverson.cryptomals.model.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class PlayerController {
    protected Logger log = Logger.getLogger(PlayerController.class.getName());

    protected PlayerRepository playerRepository;

    @Autowired
    public PlayerController(PlayerRepository repository) {
        playerRepository = repository;

        log.info("PlayerRepository says system has " +
                playerRepository.countAccounts() + " accounts.");
    }

    @RequestMapping("/player/{id}")
    public Player byId(@PathVariable("id") Long id) throws Exception {
        log.info("player-service byId() invoked: " + id);
        Player player = playerRepository.findById(id);
        log.info("player-service byId() found: " + player);

        if (player == null)
            throw new Exception("Player with ID " + id + " not found.");
        else{
            return player;
        }
    }

    @RequestMapping("/players/{name}")
    public List<Player> byName(@PathVariable("name") String partialName) throws Exception{
        log.info("player-service byName() invoked: " +
        playerRepository.getClass().getName() + " for " +
        partialName);

        List<Player> players = playerRepository
                .findByUserNameIgnoreCase(partialName);

        log.info("player-service byName() found: " + players);

        if(players == null || players.size() == 0){
            throw new Exception("Account(s) Not Found with name: " + partialName);
        }else{
            return players;
        }
    }
}
