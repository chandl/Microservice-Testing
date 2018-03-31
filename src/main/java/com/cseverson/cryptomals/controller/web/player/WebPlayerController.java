package com.cseverson.cryptomals.controller.web.player;

import com.cseverson.cryptomals.services.web.player.WebPlayerService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
public class WebPlayerController {

    @Autowired
    protected WebPlayerService playerService;

    protected Logger log = Logger.getLogger(WebPlayerController.class.getName());

    @Autowired
    public WebPlayerController(WebPlayerService playerService) {
        this.playerService = playerService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setAllowedFields("id", "userName");
    }

    @RequestMapping(value = "/player/create/{userName}", method = RequestMethod.POST)
    public ResponseEntity<ObjectNode> create(@PathVariable("userName") String userName){

        log.info("web-player-service create() invoked: " + userName);
        ResponseEntity<ObjectNode> output = playerService.create(userName);
        log.info("web-player-service create() created: " + output.toString());

        //TODO account for errors

        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(output.getBody());
    }

    @RequestMapping(value="/player/update", method=RequestMethod.PUT)
    public ResponseEntity<ObjectNode> update(@RequestBody String updatedPlayerJSON ){
        //TODO - create update()
        return null;
    }

    @RequestMapping(value="/player/delete/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<ObjectNode> delete(@PathVariable("id") Long userId){
        //TODO - create delete()
        return null;
    }

    @RequestMapping(value="/player/{id}", method=RequestMethod.GET)
    public ResponseEntity<ObjectNode> findById(@PathVariable("id") Long id){

        //TODO - create findById()

        //PlayerController will respond with a Player object - must convert using toObjectNode
        return null;
    }


    @RequestMapping(value = "players/{userName}", method=RequestMethod.GET)
    public ResponseEntity<ArrayNode> findByName(@PathVariable("userName") String userName){
        //TODO - create findByName()

        //PlayerContorller will respond with a List of Player objects. Must convert to ArrayNode
        return null;
    }

}
