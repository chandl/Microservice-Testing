package com.cseverson.cryptomals.services.web.player;

import com.cseverson.cryptomals.model.player.Player;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

/**
 * Hide the access to the PlayerService microservice inside this local service.
 *
 * @author Chandler Severson
 * @since 2018-03-30
 */
@Service
public class WebPlayerService {


    /**
    The restTemplate works because it uses a custom request-factory that uses
    Ribbon (Netflix) to look up the service to use.
     */
    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected static Logger log = Logger.getLogger(WebPlayerService.class.getName());


    public WebPlayerService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http")? serviceUrl:"http://" + serviceUrl;
    }

    public ResponseEntity<ObjectNode> create(String name){
        //TODO - create a new player with this name.
        return null;
    }

    public ResponseEntity<ObjectNode> update(Player updatedPlayer){
        //TODO - update a player
        return null;
    }

    public ResponseEntity<ObjectNode> delete(Long userId){
        //TODO - delete a player
        return null;
    }

    public ResponseEntity<ObjectNode> findById(Long id){
        // TODO - search for this id, return null if not found
        log.info("findById() invoked for: " + id);

        Player player = restTemplate.getForObject(serviceUrl + "/player/{id}", Player.class, id);

        if(player == null){
            //return not found
        }


        return null;
    }

    public ResponseEntity<ArrayNode> byUserName(String name){
        // TODO - find a list of players with a similar username.
        return null;
    }

}
