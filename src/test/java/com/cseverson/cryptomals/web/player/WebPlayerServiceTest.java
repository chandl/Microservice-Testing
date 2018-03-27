package com.cseverson.cryptomals.web.player;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class WebPlayerServiceTest {

    protected static final Long PLAYER_ID = 0L;
    protected static final String PLAYER_NAME = "spews";
    private static final Logger log = Logger.getLogger(WebPlayerServiceTest.class.getName());

    @Autowired
    WebPlayerService playerService;

    @Test
    public void findById() {
        log.info("");

    }

    @Test
    public void getById(){

    }


    @Test
    public void byUserNameContains() {

    }


    @Test
    public void byUserName(){

    }


}