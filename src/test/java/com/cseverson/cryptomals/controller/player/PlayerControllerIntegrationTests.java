package com.cseverson.cryptomals.controller.player;


import com.cseverson.cryptomals.testrunner.PlayerTestsMain;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Spring Integration/System test - by using the @SpringApplicationConfiguration,
 * it picks up the same configuration that Spring Boot would use.
 *
 * @author Chandler Severson
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PlayerTestsMain.class)
public class PlayerControllerIntegrationTests extends PlayerControllerTest {
}
