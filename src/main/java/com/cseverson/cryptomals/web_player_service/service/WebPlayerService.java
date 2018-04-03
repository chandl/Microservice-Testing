package com.cseverson.cryptomals.web_player_service.service;

import com.cseverson.cryptomals.helper.JSONError;
import com.cseverson.cryptomals.player_service.model.Player;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Performs error checking and validation before calling the PlayerService HTTP
     * Endpoint to send a request to create a new user.
     *
     * @param name The user name of the player to create.
     * @return HTTP OK or HTTP BAD_REQUEST
     */
    public ResponseEntity<ObjectNode> create(String name){
        if(name == null || name.length() == 0){
            String error = "Invalid Username Supplied when calling create()";
            log.warning(error);
            ObjectNode out= JSONError.create(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(out);
        }

        // Check for special characters in the name.
        Pattern p = Pattern.compile("[^a-zA-Z0-9.\\-_]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        if(m.find()){
            String error = "Username Contains Special Characters that are not Allowed: " + name;
            log.warning(error);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONError.create(error));
        }

        log.info("create() invoked for: " + name);
        HttpEntity<Player> request = new HttpEntity<Player>(new Player(name));

        ResponseEntity<ObjectNode> response = restTemplate.postForEntity(serviceUrl + "/player/create", request, ObjectNode.class );
        log.info("Response: " + response + ". Type: " + response.getBody().getClass());

        if(response.getStatusCode() != HttpStatus.OK){
            log.warning("Response for create() was NOT OK - " + response.getStatusCode());
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
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
