package com.cseverson.cryptomals.web_player_service.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WebPlayerControllerTest {

    @Autowired
    WebPlayerController playerController;

    //==========create==========
    @Test
    public void createPlayerSuccess() {


        //success: player is created successfully

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