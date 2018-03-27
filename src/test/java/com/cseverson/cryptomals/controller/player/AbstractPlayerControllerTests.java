package com.cseverson.cryptomals.controller.player.player;

import com.cseverson.cryptomals.controller.player.PlayerController;
import com.cseverson.cryptomals.model.player.Player;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractPlayerControllerTests {
    protected static final Long PLAYER_ID = 0L;
    protected static final String PLAYER_NAME = "spews";
    private static final Logger log = Logger.getLogger(AbstractPlayerControllerTests.class.getName());
    @Autowired
    PlayerController playerController;

    @Test
    public void validUserName() {
        log.info("Start validUserName test");

        List<Player> player = null;
        try {
            player = playerController.byName(PLAYER_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(player);
        Assert.assertEquals(player.size(), 1);
        Assert.assertEquals(player.get(0).getId(), PLAYER_ID);
        log.info("End validUserName test");
    }

    @Test
    public void invalidId(){
        log.info("Start invalidId test");
        try{
            playerController.byId(-1L);
            Assert.fail("Expected an Exception for the account not being found.");
        }catch (Exception e){

        }
        log.info("End invalidId test");
    }


    @Test
    public void invalidAccountName(){
        log.info("Start invalidAccountName test");
        try{
            playerController.byName("yoloBadName");
            Assert.fail("Expected an Exception for the account not being found.");
        }catch (Exception e){

        }
        log.info("End invalidAccountName test");
    }

}
