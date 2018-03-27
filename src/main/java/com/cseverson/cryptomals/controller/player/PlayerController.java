package com.cseverson.cryptomals.controller.player;

import com.cseverson.cryptomals.ex.PlayerNotFoundException;
import com.cseverson.cryptomals.model.player.Player;
import com.cseverson.cryptomals.model.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

/**
 * A RESTFul controller for accessing Player information.
 */
@RestController
public class PlayerController {
    protected Logger log = Logger.getLogger(PlayerController.class.getName());

    protected PlayerRepository playerRepository;

    /**
     * Create an instance plugging in the repository of Players
     *
     * @param repository
     *          A Player repository implementation.
     */
    @Autowired
    public PlayerController(PlayerRepository repository) {
        playerRepository = repository;

        log.info("PlayerRepository says system has " +
                playerRepository.countAccounts() + " players.");
    }


    /**
     * Fetch an account with the specified ID.
     *
     *
     * @param id The auto-generated player ID.
     * @return The account, if found.
     * @throws PlayerNotFoundException If the player ID is not found.
     */
    @RequestMapping(value="/player/{id}", method= RequestMethod.GET)
    public Player byId(@PathVariable("id") Long id) throws PlayerNotFoundException {
        log.info("player-service byId() invoked: " + id);
        Player player = playerRepository.findById(id);
        log.info("player-service byId() found: " + player);

        if (player == null)
            throw new PlayerNotFoundException("Player with ID " + id + " not found.");
        else{
            return player;
        }
    }

    /**
     * Fetch account(s) with the specified name. A partial case-insensitive match is supported.
     *
     * @param partialName
     * @return A non-null, non-empty List of accounts.
     * @throws PlayerNotFoundException If there are no matches at all.
     */
    @RequestMapping(value="/players/{name}", method=RequestMethod.GET)
    public List<Player> byName(@PathVariable("name") String partialName) throws PlayerNotFoundException{
        log.info("player-service byName() invoked: " +
        playerRepository.getClass().toString() + " for " +
        partialName);

        List<Player> players = playerRepository
                .findByUserNameIgnoreCase(partialName);

        log.info("player-service byName() found: " + players);

        if(players == null || players.size() == 0){
            throw new PlayerNotFoundException("Account(s) Not Found with name: " + partialName);
        }else{
            return players;
        }
    }
}
