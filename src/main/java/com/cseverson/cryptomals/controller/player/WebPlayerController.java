package com.cseverson.cryptomals.controller.player;

import com.cseverson.cryptomals.model.player.Player;
import com.cseverson.cryptomals.services.player.WebPlayerService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
public class WebPlayerController {

    @Autowired
    protected WebPlayerService playerService;

    protected Logger log = Logger.getLogger(WebPlayerController.class.getName());

    public WebPlayerController(WebPlayerService playerService) {
        this.playerService = playerService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setAllowedFields("id", "userName");
    }

    @RequestMapping("/player/create/{userName}")
    public ResponseEntity<ObjectNode> create(@PathVariable("userName") String userName){

        log.info("web-player-service create() invoked: " + userName);
        Player newPlayer = playerService.create(userName);
        log.info("web-player-service create() created: " + newPlayer);

        //TODO account for errors

        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(newPlayer.getJSONString());
    }

    //TODO - create update()

    //TODO - create delete()

    //TODO - create findById()

    //TODO - create findByName()


}
