package com.cseverson.cryptomals.helper;


import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONError {

    private JSONError(){}

    public static ObjectNode create(String errorMessage) {

        return JSONBuilder.builder()
                .add("error", errorMessage)
                .getObjectNode();
    }
}
