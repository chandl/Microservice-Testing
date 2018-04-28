package com.cseverson.cryptomals.web_player_service.service;

import com.cseverson.cryptomals.helper.JSONError;
import com.cseverson.cryptomals.player_service.model.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
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

    public ResponseEntity<ObjectNode> update(String updatedPlayerJSON){
        //TODO - update a player

        ObjectMapper mapper = new ObjectMapper();
        Player player = null;
        try {
             player = mapper.readValue(updatedPlayerJSON, Player.class);

             log.info("Found Player: " + player);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        HttpEntity<Player> found = new HttpEntity<Player>(new Player())



        return null;
    }

    public ResponseEntity<ObjectNode> delete(Long userId){
        //TODO - delete a player
        return null;
    }

    public ResponseEntity<ObjectNode> findById(Long id){
        log.info("findById() invoked for: " + id);

        if(id == null || id < 0){
            log.warning("findById() returned BAD_REQUEST for id: " + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONError.create("Invalid ID supplied."));
        }

        ObjectNode player = restTemplate.getForObject(serviceUrl + "/player/{id}", ObjectNode.class, id);

        if(player == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(JSONError.create("No Player found with this ID."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(player);
    }

    public ResponseEntity<ArrayNode> byUserName(String name){

        log.info("byUserName() invoked for: " + name);

        if(name == null || name.length() == 0){
            log.warning("byUserName() returned BAD_REQUEST for name: " + name);
            ArrayList<String> err = new ArrayList<String>();
            err.add("Invalid Player Name: " + name);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONError.createMutli(err));
        }

        ArrayNode players;
        try{
            players = restTemplate.getForObject( serviceUrl + "/players/{userName}", ArrayNode.class, name);
        }catch(HttpServerErrorException ex){ // An exception will be thrown when no player is found.
            log.warning("byUserName() returned NOT_FOUND. No player found with name: " + name);
            ArrayList<String> err = new ArrayList<String>();
            err.add("No Player found with name: " + name);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(JSONError.createMutli(err));
        }

        return ResponseEntity.status(HttpStatus.OK).body(players);
    }

}
