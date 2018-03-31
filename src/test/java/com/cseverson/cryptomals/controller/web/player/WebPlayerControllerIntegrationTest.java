package com.cseverson.cryptomals.controller.web.player;

import com.cseverson.cryptomals.services.web.player.WebPlayerServer;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebPlayerServer.class)
public class WebPlayerControllerIntegrationTest extends WebPlayerControllerTest{
}
