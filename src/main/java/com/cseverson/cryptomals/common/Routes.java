package com.cseverson.cryptomals.common;

public interface Routes {

    // PlayerController - player_service
    String GET_PLAYER_BY_ID = "/player/{id}";
    String GET_PLAYERS_BY_NAME = "/players/{userName}";
    String POST_CREATE_PLAYER = "/player/create";
    String PUT_UPDATE_PLAYER = "/player/update";
    String DELETE_USER = "/player/delete/{id}";


    // WebPlayerController - web_player_service
    String W_POST_CREATE_PLAYER = "/player/create/{userName}";
}
