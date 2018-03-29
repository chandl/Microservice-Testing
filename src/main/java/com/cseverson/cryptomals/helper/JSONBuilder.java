package com.cseverson.cryptomals.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONBuilder {

    private ObjectMapper mapper;
    private ArrayNode arrayNode;
    private ObjectNode response;
    private JSONBuilder instance;

    private JSONBuilder(){
        mapper = new ObjectMapper();
        arrayNode = mapper.createArrayNode();
        response = mapper.createObjectNode();
        instance = this;
    }

    public static JSONBuilder builder(){
        return new JSONBuilder();
    }

    public JSONBuilder add(String key, String value){
        response.put(key,value);

        return instance;
    }

    public ObjectNode getObjectNode(){
        return response;
    }
}
