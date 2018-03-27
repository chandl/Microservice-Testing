package com.cseverson.cryptomals.web.player;

import com.cseverson.cryptomals.player.PlayerTestsMain;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PlayerTestsMain.class )
public class WebPlayerServiceIntegrationTest extends WebPlayerServiceTest{
}
