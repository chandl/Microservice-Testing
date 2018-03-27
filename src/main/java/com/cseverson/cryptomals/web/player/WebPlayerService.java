package com.cseverson.cryptomals.web.player;

import com.cseverson.cryptomals.model.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@Service
public class WebPlayerService {


    /*
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


    public Player findById(Long id){
        // TODO - search for this id, return null if not found
        return null;
    }

    public List<Player> byUserNameContains(String name){
        // TODO - find a list of players with a similar username.
        return null;
    }

    public Player byUserName(String name){
        // TODO - explicitly find a player by name.
        return null;
    }

    public Player getById(Long id){
        // TODO - search for this id, throw an exception if not found
        return null;
    }

}
