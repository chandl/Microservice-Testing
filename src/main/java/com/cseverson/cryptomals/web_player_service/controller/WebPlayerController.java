package com.cseverson.cryptomals.web_player_service.controller;

import com.cseverson.cryptomals.common.Const;
import com.cseverson.cryptomals.common.Routes;
import com.cseverson.cryptomals.player_service.model.Player;
import com.cseverson.cryptomals.web_player_service.service.WebPlayerService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        binder.setAllowedFields(Const.PLAYER_ID, Const.PLAYER_USERNAME);
    }

    /**
     * Front end request mapping to create a player.
     * Is routed through the WebPlayerService to call the PlayerService HTTP endpoint.
     *
     * @param userName The user name of the player to be created.
     * @return HTTP OK or HTTP BAD_REQUEST
     */
    @RequestMapping(value = Routes.W_POST_CREATE_PLAYER, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> create(@PathVariable(Const.PLAYER_USERNAME) String userName){

        log.info("web-player-service create() invoked: " + userName);
        ResponseEntity<ObjectNode> output = playerService.create(userName);
        log.info("web-player-service create() received: " + output.toString());

        return ResponseEntity.status(output.getStatusCode()).header("Content-Type", "application/json").body( output.getBody());
    }

    @RequestMapping(value=Routes.PUT_UPDATE_PLAYER, method=RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> update(@RequestBody String updatedPlayerJSON ){

        log.info("web-player-service update() invoked:" + updatedPlayerJSON);
        ResponseEntity<ObjectNode> output = playerService.update(updatedPlayerJSON);
        log.info("web-player-service update received: " + output.toString());


        return ResponseEntity.status(output.getStatusCode()).header("Content-Type", "application/json").body(output.getBody());
    }

    @RequestMapping(value=Routes.DELETE_USER, method=RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> delete(@PathVariable(Const.PLAYER_ID) Long userId){
        //TODO - create delete()
        return null;
    }

    @RequestMapping(value=Routes.GET_PLAYER_BY_ID, method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> findById(@PathVariable(Const.PLAYER_ID) Long id){

        //TODO - create findById()
        log.info("web-player-service findById() invoked:" + id);
        ResponseEntity<ObjectNode> output = playerService.findById(id);
        log.info("web-player-service findById received: " + output.toString());


        return ResponseEntity.status(output.getStatusCode()).header("Content-Type", "application/json").body(output.getBody());
    }


    @RequestMapping(value = Routes.GET_PLAYERS_BY_NAME, method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayNode> findByName(@PathVariable(Const.PLAYER_USERNAME) String userName){
        //TODO - create findByName()

        //PlayerContorller will respond with a List of Player objects. Must convert to ArrayNode
        return null;
    }

}
