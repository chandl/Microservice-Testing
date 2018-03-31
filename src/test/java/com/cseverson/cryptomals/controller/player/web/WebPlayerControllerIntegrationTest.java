package com.cseverson.cryptomals.controller.player.web;

import com.cseverson.cryptomals.testrunner.PlayerTestsMain;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PlayerTestsMain.class)
public class WebPlayerControllerIntegrationTest extends WebPlayerControllerTest{
}
