package com.cseverson.cryptomals.player_service.controller;

import com.cseverson.cryptomals.common.Const;
import com.cseverson.cryptomals.common.Routes;
import com.cseverson.cryptomals.player_service.ex.PlayerNotFoundException;
import com.cseverson.cryptomals.helper.JSONError;
import com.cseverson.cryptomals.player_service.model.Player;
import com.cseverson.cryptomals.player_service.model.PlayerRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

/**
 * A RESTFul controller for accessing Player information.
 */
@RestController
public class PlayerController {
    private final Logger log = Logger.getLogger(PlayerController.class.getName());

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
    }

    /**
     * Fetch an account with the specified ID.
     *
     * @param id
     *          The auto-generated player ID.
     * @return The account, if found.
     */
    @RequestMapping(value=Routes.GET_PLAYER_BY_ID, method= RequestMethod.GET)
    public Player byId(@PathVariable(Const.PLAYER_ID) Long id)  {
        log.info("player-service byId() invoked: " + id);
        Player player = playerRepository.findById(id);
        log.info("player-service byId() found: " + player);

        if (player == null){
            log.warning("Player with ID " + id + " not found.");
            return null;
        }
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
    @RequestMapping(value=Routes.GET_PLAYERS_BY_NAME, method=RequestMethod.GET)
    public List<Player> byName(@PathVariable(Const.PLAYER_USERNAME) String partialName) throws PlayerNotFoundException{
        log.info("player-service byName() invoked: for " +
        partialName);

        List<Player> players = playerRepository
                .findByUserNameContainingIgnoreCase(partialName);

        log.info("player-service byName() found: " + players);

        if(players == null || players.size() == 0){
            throw new PlayerNotFoundException("Account(s) Not Found with name: " + partialName);
        }else{
            return players;
        }
    }

    /**
     * Gets all Players from the PlayerRepository.
     *
     * Used for Testing .
     *
     * @return A list of all players in the database.
     */
    public List<Player> getAllPlayers(){
        return Lists.newArrayList(playerRepository.findAll());
    }

    /**
     * Adds the specified player to the database.
     *
     * @param newPlayer The player to add to the database.
     * @return BAD_REQUEST or OK
     */
    @RequestMapping(value=Routes.POST_CREATE_PLAYER, method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Player newPlayer){
        log.info("player-service create() invoked for: " + newPlayer);

        if(newPlayer == null){ //Send an error response.
            ObjectNode error = JSONError.create("Null Player Specified");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        //Save the new player.
        playerRepository.save(newPlayer);
        ResponseEntity<ObjectNode> response = ResponseEntity.status(HttpStatus.OK).body(newPlayer.toObjectNode());

        log.info("player-service create() returned: " +  response);

        return response;
    }

    /**
     * Update an existing player in the database.
     *
     * @param updatedPlayer The updated player information.
     * @return BAD_REQUEST or OK
     */
    @RequestMapping(value=Routes.PUT_UPDATE_PLAYER, method=RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable(Const.PLAYER_ID) Long userId, @RequestBody ObjectNode updatedPlayer){
        log.info("player-service update() invoked for id: " + userId + ". Updated: " + updatedPlayer);

        if(userId == null || userId < 0){
            String error = "Invalid User ID supplied";
            log.warning(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONError.create(error));
        }

        //handle null user supplied
        if(updatedPlayer == null ){
            String error = "Null Updated Player supplied";
            log.warning(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONError.create(error));
        }

        if(updatedPlayer.get(Const.PLAYER_ID).asLong() != userId){
            String error = "Supplied Player ID Does not match Updated Player Info.";
            log.warning(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONError.create(error));
        }

        Player toUpdate;
        if((toUpdate=  playerRepository.findById(userId)) == null ){
            String error = "Player with id "+ userId +" not found in the Database. ";
            log.info(error);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(JSONError.create(error));
        }


        int newAdsViewed = updatedPlayer.get(Const.PLAYER_ADS_VIEWED).asInt();
        if(toUpdate.getAdsViewed() != newAdsViewed){
            log.info("Updating ads viewed for id: " + userId + ". Old: " + toUpdate.getAdsViewed() +". New: " + newAdsViewed);
            toUpdate.setAdsViewed(newAdsViewed);
        }

        Timestamp newHeartDate =Timestamp.valueOf(updatedPlayer.get(Const.PLAYER_NEXT_HEART_DATE).asText());
        if(toUpdate.getNextHeartTime().getTime() != newHeartDate.getTime()){
            log.info("Updating new heart date for id: " + userId + ". Old: " + toUpdate.getNextHeartTime() +". New: " + newHeartDate);
            toUpdate.setNextHeartTime(new Timestamp(newHeartDate.getTime()));
        }

        int newHeartCount = updatedPlayer.get(Const.PLAYER_HEART_COUNT).asInt();
        if(toUpdate.getHeartCount() != newHeartCount){
            log.info("Updating heart count for id: " + userId + ". Old: " + toUpdate.getHeartCount()  +". New: " + newHeartCount);
            toUpdate.setHeartCount(newHeartCount);
        }

        Long newTimePlayed = updatedPlayer.get(Const.PLAYER_TIME_PLAYED).asLong();
        if(toUpdate.getTimePlayed() != newTimePlayed){
            log.info("Updating time played for id: " + userId + ". Old: " + toUpdate.getTimePlayed()  +". New: " + newTimePlayed);
            toUpdate.setTimePlayed(newTimePlayed);
        }

        playerRepository.save(toUpdate);
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(toUpdate.toObjectNode());
    }

    /**
     * Deletes a player from the database with the specified ID.
     *
     * @param userId The ID of the user to delete
     * @return BAD_REQUEST, NOT_FOUND, or OK
     */
    @RequestMapping(value=Routes.DELETE_USER, method=RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable(Const.PLAYER_ID) Long userId){
        log.info("player-service delete() invoked for id: " + userId);

        //handle null user supplied
        if( userId == null ){
            String error = "Null Player ID supplied";
            log.info(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONError.create(error));
        }

        //handle bad ID user
        Player toDelete;
        if((toDelete = playerRepository.findById(userId)) == null){
            String error = "Player ID not found: " + userId;
            log.info(error);
            ObjectNode out = JSONError.create(error);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(out);
        }

        playerRepository.delete(toDelete);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
