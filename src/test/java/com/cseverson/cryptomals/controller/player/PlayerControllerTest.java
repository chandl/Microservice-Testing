package com.cseverson.cryptomals.controller.player;

import com.cseverson.cryptomals.model.player.Player;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;

public abstract class PlayerControllerTest {
    protected static final Long PLAYER_ID = 0L;
    protected static final String PLAYER_NAME = "spews";
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
        //TODO
        return ;
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

}
