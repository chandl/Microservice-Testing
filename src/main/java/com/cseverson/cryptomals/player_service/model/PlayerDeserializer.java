package com.cseverson.cryptomals.player_service.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.logging.Logger;

public class PlayerDeserializer extends JsonDeserializer {

    private static Logger log = Logger.getLogger(PlayerDeserializer.class.getName());

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        log.info("PlayerDeserializer deserialize() invoked. ");
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        Long id = node.get("id").asLong();
        log.info("ID: " + id);
        if(id < 0L){

        }

        return new Player(node.get("userName").asText());
    }
}
