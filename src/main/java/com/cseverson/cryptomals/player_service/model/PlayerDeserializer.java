package com.cseverson.cryptomals.player_service.model;

import com.cseverson.cryptomals.common.Const;
import com.cseverson.cryptomals.player_service.controller.PlayerController;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.logging.Logger;

public class PlayerDeserializer extends JsonDeserializer {

    private static Logger log = Logger.getLogger(PlayerDeserializer.class.getName());

    @Autowired
    PlayerController controller;


    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException{
        log.info("PlayerDeserializer deserialize() invoked. ");
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        Long id = node.get(Const.PLAYER_ID).asLong();
        if(node.get(Const.PLAYER_ID).isNull()){ // Player not found.
            String name = node.get(Const.PLAYER_USERNAME).asText();

            if(node.get(Const.PLAYER_USERNAME).isNull() || name.equals("")){
                log.warning("FAILED to deserialize. Username not supplied: " + node.toString());
                return null;
            }

            Player p = new Player(name);
            log.info("Supplied Player ID is null. Returning new player:  " + p);
            return p;
        }else{
            Player p = controller.byId(id);

            if(p == null){
                log.warning("FAILED to find player with ID: " +id + ". Request: " + node.toString());
                return null;
            }

            return p;
        }

    }
}
