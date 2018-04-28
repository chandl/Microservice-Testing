package com.cseverson.cryptomals.web_player_service.controller;

import com.cseverson.cryptomals.common.Const;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public void updatePlayerNullName() {

        //fail: supplied player is null
        //fail: supplied player is unknown (not in DB)
        //success: player is successfully updated

    }

    @Test
    public void updateUnknownPlayer() {

    }

    @Test
    public void updatePlayerSuccess() {
        log.info("start updatePlayerSuccess()");

        ResponseEntity<ObjectNode> response = playerController.update(FAKE_USERNAME);
        log.info("Response: "+ response);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        ObjectNode node = response.getBody();

        String userName = node.get(Const.PLAYER_USERNAME).asText();
        Assert.assertEquals(FAKE_USERNAME, userName);

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

        //fail: user ID supplied is null
        //fail: user ID not found
        //success: player is found successfully
    }

    @Test
    public void findByInvalidId() {
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
//        Assert.assertEquals(FAKE_USERNAME, userName);

        log.info("end findByIdSuccess()");
    }


    //==========findByName==========
    @Test
    public void findByNullName() {

        //fail: username null
        //fail: username not found
        //success: one user found.
        //success: multiple users found.
    }

    @Test
    public void findByInvalidName() {

    }

    @Test
    public void findByNameOneUser() {

    }

    @Test
    public void findByNameMultiUser() {

    }
}