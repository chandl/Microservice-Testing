package com.cseverson.cryptomals.web_player_service.controller;

import com.cseverson.cryptomals.common.Const;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.logging.Logger;

public class WebPlayerControllerTest {
    private static final Logger log = Logger.getLogger(WebPlayerControllerTest.class.getName());
    private static final String FAKE_USERNAME = "Mr-Faker_123";

    @Autowired
    WebPlayerController playerController;

    //==========create==========
    @Test
    public void createPlayerSuccess() {
        log.info("start createPlayerSuccess()");

        ResponseEntity<ObjectNode> response = playerController.create(FAKE_USERNAME);
        log.info("Response: "+ response);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        ObjectNode node = response.getBody();

        String userName = node.get(Const.PLAYER_USERNAME).asText();
        Assert.assertEquals(FAKE_USERNAME, userName);

        log.info("end createPlayerSuccess()");
    }

    @Test
    public void createPlayerBlankName() {
        log.info("start createPlayerBlankName()");

        ResponseEntity<ObjectNode> response= playerController.create("");
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        log.info("end createPlayerBlankName()");
    }

    @Test
    public void createPlayerNullName() {
        log.info("start createPlayerNullName()");

        ResponseEntity<ObjectNode> response = playerController.create(null);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        log.info("end createPlayerNullName()");
    }

    @Test
    public void createPlayerInvalidRegexName() {
        log.info("start createPlayerInvalidRegexName");

        ResponseEntity<ObjectNode> response = response = playerController.create("\n\f\r\b\t\\\"\'");
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        log.info("end createPlayerInvalidRegexName");
    }

    //==========update==========
    @Test
    public void updatePlayerNull() {
        ResponseEntity<ObjectNode> re = playerController.update(1L, null);

        Assert.assertNotNull(re);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, re.getStatusCode());
    }

    @Test
    public void updatePlayerBadId() {
        ResponseEntity<ObjectNode> re = playerController.update(-1L, null);

        Assert.assertNotNull(re);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, re.getStatusCode());
    }

    @Test
    public void updatePlayerMismatch() {
        ResponseEntity<ObjectNode> res = playerController.create(UUID.randomUUID().toString());
        ObjectNode node = res.getBody();

        ResponseEntity<ObjectNode> re = playerController.update(2L, node.toString());

        Assert.assertNotNull(re);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, re.getStatusCode());
    }

    @Test
    public void updatePlayerNotFound() {
        ResponseEntity<ObjectNode> res = playerController.create(UUID.randomUUID().toString());
        ObjectNode node = res.getBody();

        node.put(Const.PLAYER_ID, 1000L);
        ResponseEntity<ObjectNode> re = playerController.update(1000L, node.toString());

        Assert.assertNotNull(re);
        Assert.assertEquals(HttpStatus.NOT_FOUND, re.getStatusCode());
    }

    @Test
    public void updatePlayerSuccess() {
        // ads viewed, time played, heartCount, nextHeartTime

        log.info("start updatePlayerSuccess()");

        ResponseEntity<ObjectNode> re = playerController.create(UUID.randomUUID().toString());


        ObjectNode node = re.getBody();
        int upOne = node.get(Const.PLAYER_HEART_COUNT).asInt() +1;
        node.put(Const.PLAYER_HEART_COUNT,  upOne);

        int oneMoreAd = node.get(Const.PLAYER_ADS_VIEWED).asInt() + 1;
        node.put(Const.PLAYER_ADS_VIEWED, oneMoreAd);

        long moreTimePlayed = node.get(Const.PLAYER_TIME_PLAYED).asLong() + 1000L;
        node.put(Const.PLAYER_TIME_PLAYED, moreTimePlayed);

        Timestamp nextHeartTime = new Timestamp(System.currentTimeMillis());
        node.put(Const.PLAYER_NEXT_HEART_DATE, nextHeartTime.toString());


        ResponseEntity<ObjectNode> response = playerController.update(node.get(Const.PLAYER_ID).asLong(), node.toString());
        log.info("Update Response: "+ response);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        ObjectNode reno = response.getBody();
        Assert.assertEquals(upOne, reno.get(Const.PLAYER_HEART_COUNT).asInt());
        Assert.assertEquals(oneMoreAd, reno.get(Const.PLAYER_ADS_VIEWED).asInt());
        Assert.assertEquals(moreTimePlayed, reno.get(Const.PLAYER_TIME_PLAYED).asLong());
        Assert.assertEquals(nextHeartTime.toString(), reno.get(Const.PLAYER_NEXT_HEART_DATE).asText());

        log.info("end updatePlayerSuccess()");
    }

    //==========delete==========

    @Test
    public void deletePlayerNullId() {

        //fail: user ID is null
        //fail: user ID is invalid
        //success: player is successfully deleted
    }

    @Test
    public void deletePlayerInvalidId() {

    }

    @Test
    public void deletePlayerSuccess() {

    }

    //==========findById==========
    @Test
    public void findByNullId() {

        log.info("start findByNullId()");

        ResponseEntity<ObjectNode> response = playerController.findById(null);
        log.info("Response: "+ response);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        log.info("end findByNullId()");
    }

    @Test
    public void findByInvalidId() {

        log.info("start findByInvalidId()");

        ResponseEntity<ObjectNode> response = playerController.findById(100000L);
        log.info("Response: "+ response);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        log.info("end findByInvalidId()");
    }

    @Test
    public void findByIdSuccess() {
        log.info("start findByIdSuccess()");

        ResponseEntity<ObjectNode> response = playerController.findById(1L);
        log.info("Response: "+ response);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        ObjectNode node = response.getBody();
        String userName = node.get(Const.PLAYER_USERNAME).asText();

        log.info("Output: " + node.toString());
        Assert.assertEquals("spews", userName);

        log.info("end findByIdSuccess()");
    }


    //==========findByName==========
    @Test
    public void findByNullName() {
        log.info("start findByNullName()");

        ResponseEntity<ArrayNode> response = playerController.findByName(null);
        log.info("Response: "+ response);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ArrayNode node = response.getBody();
        Assert.assertEquals(1, node.size());

        JsonNode err = node.get(0);
        String error = err.get("error").asText();

        log.info("Output: " + node.toString() + ". Error:" + error);
        Assert.assertNotNull(error);
        Assert.assertTrue(error.length() > 0);

        log.info("end findByNullName()");
    }

    @Test
    public void findByBadRegexName() {
        log.info("start findByBadRegexName");

        ResponseEntity<ObjectNode> response = response = playerController.create("\n\f\r\b\t\\\"\'");

        log.info("end findByBadRegexName");
    }

    @Test
    public void findByInvalidName() {
        // aklsjdflkjuyioygio1982903890u
        log.info("start findByInvalidName()");

        ResponseEntity<ArrayNode> response = playerController.findByName("aklsjdflkjuyioygio1982903890u");
        log.info("Response: "+ response);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ArrayNode node = response.getBody();
        Assert.assertEquals(1, node.size());

        JsonNode err = node.get(0);
        String error = err.get("error").asText();

        log.info("Output: " + node.toString() + ". Error:" + error);
        Assert.assertNotNull(error);
        Assert.assertTrue(error.length() > 0);

        log.info("end findByInvalidName()");
    }

    @Test
    public void findByNameOneUser() {
        // chandler

        log.info("start findByNameOneUser()");

        ResponseEntity<ArrayNode> response = playerController.findByName("chandler");
        log.info("Response: "+ response);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        ArrayNode node = response.getBody();
        Assert.assertEquals(1, node.size());

        JsonNode p = node.get(0);
        String userName = p.get(Const.PLAYER_USERNAME).asText();

        log.info("Output: " + node.toString() + ". Player 0:" + p.toString());
        Assert.assertEquals("chandler", userName);

        log.info("end findByNameOneUser()");
    }

    @Test
    public void findByNameMultiUser() {
        //same & same

        log.info("start findByNameMultiUser()");

        ResponseEntity<ArrayNode> response = playerController.findByName("same");
        log.info("Response: "+ response);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        ArrayNode node = response.getBody();
        Assert.assertEquals(2, node.size());

        JsonNode p1 = node.get(0);
        String userName = p1.get(Const.PLAYER_USERNAME).asText();

        log.info("Output: " + node.toString());
        Assert.assertEquals("same", userName);

        log.info("end findByNameMultiUser()");
    }
}