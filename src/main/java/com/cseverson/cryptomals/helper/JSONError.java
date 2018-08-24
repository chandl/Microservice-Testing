package com.cseverson.cryptomals.helper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class JSONError {

    private JSONError(){}

    public static ObjectNode create(String errorMessage) {

        return JSONBuilder.builder()
                .add("error", errorMessage)
                .getObjectNode();
    }

    public static ArrayNode createMutli(ArrayList<String> errorMessages) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode dataTable = mapper.createObjectNode();
        ArrayNode aa = dataTable.putArray("errors");

        for(String error: errorMessages){
            aa.add(create(error));
        }

        return aa;
    }
}
