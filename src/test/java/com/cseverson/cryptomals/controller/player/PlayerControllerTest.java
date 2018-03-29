package com.cseverson.cryptomals.controller.player;

import com.cseverson.cryptomals.ex.PlayerNotFoundException;
import com.cseverson.cryptomals.model.player.Player;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


public abstract class PlayerControllerTest {
    protected static final Long PLAYER_ID = 0L;
    protected static final String PLAYER_NAME = "spews";

    protected static final String TWO_DUPLICATE_NAME = "duplicateName";
    protected static final String TWO_EXACT_NAME = "same";

    protected static final String TEST_USER = "PlayerControllerTestPlayer";
    protected static final String TEST_DELETE_USER = "PlayerToDelete";
    protected static final String TEST_UPDATE_USER = "PlayerToUpdate";

    private static final Logger log = Logger.getLogger(PlayerControllerTest.class.getName());


    @Autowired
    PlayerController playerController;

    @Test
    public void findByName() {
        log.info("Start findByName test");

        List<Player> player = null;
        try {
            player = playerController.byName(PLAYER_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(player);
        Assert.assertEquals(player.size(), 1);
        Assert.assertEquals(player.get(0).getId(), PLAYER_ID);
        log.info("End findByName test");
    }

    @Test
    public void findById(){
        log.info("Start findById test");

        try{
            Player p = playerController.byId(PLAYER_ID);

            Assert.assertNotNull(p);
            Assert.assertEquals(p.getUserName(), PLAYER_NAME);

        } catch (PlayerNotFoundException ex){
            Assert.fail("Caught an exception while finding player by ID.");
            ex.printStackTrace();
        }

        log.info("End findById test");
    }

    @Test
    public void findByIdFail(){
        log.info("Start findByIdFail test");
        try{
            playerController.byId(-1L);
            Assert.fail("Expected an Exception for the account not being found.");
        }catch (Exception e){

        }
        log.info("End findByIdFail test");
    }


    @Test
    public void findByNameFail(){
        log.info("Start findByNameFail test");
        try{
            playerController.byName("yoloBadName");
            Assert.fail("Expected an Exception for the account not being found.");
        }catch (Exception e){

        }
        log.info("End findByNameFail test");
    }


    @Test
    public void findSimilarNames(){
        log.info("Start findSimilarNames test");
        List<Player> player = null;
        try {
            player = playerController.byName(TWO_DUPLICATE_NAME);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertNotNull(player);
        Assert.assertEquals(player.size(), 2);

        for(Player p : player){
            if(!p.getUserName().contains(TWO_DUPLICATE_NAME)){
                Assert.fail("Invalid Player Returned: " + p);
            }
        }

        log.info("End findSimilarNames test");
    }

    @Test
    public void findExactNames(){
        log.info("Start findExactNames test");

        List<Player> player = null;
        try {
            player = playerController.byName(TWO_EXACT_NAME);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertNotNull(player);
        Assert.assertEquals(player.size(), 2);

        for(Player p : player){
            if(!p.getUserName().equals(TWO_EXACT_NAME)){
                Assert.fail("Invalid Player Returned: " + p);
            }
        }

        log.info("End findExactNames test");
    }

    @Test
    public void createUserNull() {
        log.info("Start createUserNull test");

        ResponseEntity<?> response = playerController.create(null);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        log.info("Bad Response Body: " + response.getBody() + ". Headers: "+ response.getHeaders());

        //Parse the error to make sure we are getting the correct error msg.
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonResponse = mapper.readTree( response.getBody().toString());
            Assert.assertEquals(jsonResponse.get("error").asText(), "Null Player Specified");

        } catch (IOException e) {
            Assert.fail("Could not read JSON Response.");
            e.printStackTrace();
        }
    }

    @Test
    public void createUser(){
        log.info("Start createUser test");

        Player newPlayer = new Player(TEST_USER);
        ResponseEntity<?> response = playerController.create(newPlayer);
        Assert.assertNotNull(response);

        log.info("CreateUser response: " + response.getBody() + ". Headers: " + response.getHeaders());

        //Make sure the response is not null and has the appropriate status.
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonResponse = mapper.readTree( response.getBody().toString() );
            Assert.assertNotNull(jsonResponse);
            log.info("JsonResponse: " + jsonResponse);

        } catch (IOException e) {
            Assert.fail("Failure parsing JSON Response.");
            e.printStackTrace();
        }

        //Make sure we can find the new player in the DB.
        try {
            List<Player> playersFind = playerController.byName(TEST_USER);

            Assert.assertNotNull(playersFind);
            Assert.assertEquals(playersFind.size(), 1);
            Assert.assertEquals(playersFind.get(0).getUserName(), TEST_USER);

        } catch (PlayerNotFoundException e) {
            Assert.fail("Player Not found in Database." + e.getMessage());
        }

        log.info("End createUser test");
    }

    @Test
    public void updateUser(){
        log.info("Start updateUser test");
        Player toUpdate = null;
        if((toUpdate = findByUserName(TEST_UPDATE_USER)) == null){
           Assert.fail("Failure Validating Player: " + TEST_UPDATE_USER);
        }

        Assert.assertEquals(toUpdate.getAdsViewed(), 0);
        Assert.assertFalse(toUpdate.isAdmin()); //make sure player is not admin prior to updates

        toUpdate.setAdsViewed(10);
        toUpdate.setAdminStatus(true); //set player as admin
        ResponseEntity<?> response = playerController.update(toUpdate.getId(), toUpdate); //update player in DB

        //TODO analyze response

        Player updated = findByUserName(TEST_UPDATE_USER);
        Assert.assertTrue(updated.isAdmin()); //make sure the new, found user is an admin now
        Assert.assertEquals(updated.getAdsViewed(), 10);
        log.info("End updateUser test");
    }

    @Test
    public void deleteUser(){
        log.info("Start deleteUser test");
        Player toDelete = null;
        if((toDelete = findByUserName(TEST_DELETE_USER)) == null){
            Assert.fail("Failure Validating Player: " + TEST_DELETE_USER);
        }

        ResponseEntity<?> response = playerController.delete(toDelete.getId());

        //TODO analyze response

        try {
            Player deleted = null;
            if((deleted = playerController.byId(toDelete.getId())) != null){
                Assert.fail("Deleted player is still returning as non-null: " + deleted);
            }
        } catch (PlayerNotFoundException e) {
            //Success!
        }

        log.info("End deleteUser test");
    }

    /**
     * Helper method to list all players for debugging.
     */
    private void listAllPlayers(){
        int i = 0;
        for(Player p: playerController.getAllPlayers()){
            log.info("Player " + i + ": " + p);
            i++;
        }
    }

    private Player findByUserName(String name){
        log.info("findByUserName() invoked:");
        try { //make sure player exists
            List<Player> playersFind = playerController.byName(name);

            Assert.assertNotNull(playersFind);
            Assert.assertEquals(playersFind.size(), 1);
            Assert.assertEquals(playersFind.get(0).getUserName(), name);

            return(playersFind.get(0));

        } catch (PlayerNotFoundException e) {
            Assert.fail("Player Not found in Database." + e.getMessage());
        }

        return null;
    }

}
