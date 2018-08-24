package com.cseverson.cryptomals.web_player_service.controller;

import com.cseverson.cryptomals.web_player_service.service.WebPlayerServer;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebPlayerServer.class)
@TestPropertySource("classpath:/web_player_service/web-player-server.properties")
public class WebPlayerControllerIntegrationTest extends WebPlayerControllerTest{

}
