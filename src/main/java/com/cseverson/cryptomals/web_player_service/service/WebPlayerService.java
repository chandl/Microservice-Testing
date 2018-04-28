package com.cseverson.cryptomals.web_player_service.service;

import com.cseverson.cryptomals.helper.JSONBuilder;
import com.cseverson.cryptomals.helper.JSONError;
import com.cseverson.cryptomals.player_service.model.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

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

    /**
     * Performs error checking and validation before calling the PlayerService HTTP
     * Endpoint to send a request to update an existing user.
     *
     * @param playerId The user ID of the user to update.
     * @param updatedPlayerJSON The updated JSON of the new user.
     * @return HTTP OK or HTTP BAD_REQUEST
     */
    public ResponseEntity<ObjectNode> update(Long playerId, String updatedPlayerJSON){

        log.info("update() invoked for id: " + playerId + ". Updated: " + updatedPlayerJSON);

        if(playerId == null || playerId < 0){
            String error = "Invalid Player ID supplied: " + playerId;
            log.warning(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONError.create(error));
        }

        if(updatedPlayerJSON == null ){
            String error = "No Updated Player Information supplied";
            log.warning(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONError.create(error));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(updatedPlayerJSON, headers);
        ResponseEntity<ObjectNode> response = null;
        try{
            response = restTemplate.exchange(serviceUrl + "/player/update/{id}", HttpMethod.PUT, entity, ObjectNode.class, playerId);

        }catch(HttpStatusCodeException ex){ //RestTemplate.exchange() throws exceptions - we must catch in order to pass along the underlying issue.
            try { //handle errors in a funky way since we call exchange() on RestTemplate
                ObjectNode node = (ObjectNode) new ObjectMapper().readTree(ex.getResponseBodyAsString());
                log.warning("update() encountered an exception for id: " + playerId+". Response: " + node);
                return ResponseEntity.status(ex.getStatusCode()).body(node);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        log.info("update() returned " + response +" for id: " + playerId + '.');

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

    /**
     * Performs error checking and validation before calling the PlayerService HTTP
     * Endpoint to send a request to delete an existing user.
     *
     * @param userId the Player ID of the user to be deleted.
     * @return HTTP OK (if deleted), HTTP BAD_REQUEST (errors in request), or HTTP NOT_FOUND (player not found)
     */
    public ResponseEntity<ObjectNode> delete(Long userId){
        log.info("delete() invoked for: " + userId);

        if(userId == null || userId < 0){
            String error = "Invalid Player ID supplied: " + userId;
            log.warning(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONError.create(error));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        ResponseEntity<ObjectNode> response = null;

        try{
            response = restTemplate.exchange(serviceUrl + "/player/delete/{id}", HttpMethod.DELETE, entity, ObjectNode.class, userId);

        }catch(HttpStatusCodeException ex){

            try { //handle errors in a funky way since we call exchange() on RestTemplate
                ObjectNode node = (ObjectNode) new ObjectMapper().readTree(ex.getResponseBodyAsString());
                log.warning("delete() encountered an exception for id: " + userId +". Response: " + node);
                return ResponseEntity.status(ex.getStatusCode()).body(node);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

    /**
     * Performs error checking and then calls the PlayerService HTTP endpoint to
     * find a single user by ID.
     *
     * @param id The ID of the user to search for.
     * @return HTTP BAD_REQUEST (errors in request), HTTP OK (player returned), or HTTP NOT_FOUND (player not found)
     */
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

    /**
     * Performs error validation, input sanitization, and then queries the PlayerService
     * HTTP Endpoint to find Player(s) by username.
     *
     * @param name The username to search for.
     * @return A list of players that are found with similar usernames.
     */
    public ResponseEntity<ArrayNode> byUserName(String name){

        log.info("byUserName() invoked for: " + name);

        if(name == null || name.length() == 0){
            log.warning("byUserName() returned BAD_REQUEST for name: " + name);
            ArrayList<String> err = new ArrayList<String>();
            err.add("Invalid Player Name: " + name);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONError.createMutli(err));
        }

        // Check for special characters in the name.
        Pattern p = Pattern.compile("[^a-zA-Z0-9.\\-_]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        if(m.find()){
            String error = "Username Contains Special Characters that are not Allowed: " + name;
            log.warning(error);
            ArrayList<String> errs = new ArrayList<String>();
            errs.add(error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONError.createMutli(errs));
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
