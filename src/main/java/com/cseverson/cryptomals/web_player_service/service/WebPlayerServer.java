package com.cseverson.cryptomals.web_player_service.service;

import com.cseverson.cryptomals.web_player_service.controller.WebPlayerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Player web-server. Works as a microservice client, fetching data from the
 * WebPlayerService. Uses the Discover Server (Eureka) to find the underlying PlayerService.
 *
 * @author Chandler Severson
 * @since 2018-03-30
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false)
public class WebPlayerServer {

    /**
     * URL uses the logical name of player-service: case-insensitive.
     */
    public static final String PLAYER_SERVICE_URL = "http://PLAYER-SERVICE";

    public static void main(String[] args){
        System.setProperty("spring.config.name", "web_player_service/web-player-server");
        SpringApplication.run(WebPlayerServer.class, args);
    }


    /**
     * Customized RestTemplate that has the ribbon load balancer built in.
     */
    @LoadBalanced
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * The WebPlayerService encapsulates the interaction with the microservice.
     *
     * @return A new service instance.
     */
    @Bean
    public WebPlayerService playerService() {
        return new WebPlayerService(PLAYER_SERVICE_URL);
    }

    /**
     * Create the controller, passing it the {@link WebPlayerService} to use.
     */
    @Bean
    public WebPlayerController playerController() {
        return new WebPlayerController(playerService());
    }
}
