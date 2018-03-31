package com.cseverson.cryptomals.player_service.model;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * The Player Spring Configuration for JPA.
 *
 * @author Chandler Severson
 */
@Configuration
@ComponentScan(basePackages = {"com.cseverson.cryptomals.player_service.model", "com.cseverson.cryptomals.player_service.controller"})
@EntityScan("com.cseverson.cryptomals.player_service.model") //Specify where JPA @Entity classes are
@EnableJpaRepositories("com.cseverson.cryptomals.player_service.model") // Look for classes extending Spring Data's 'Repository marker interface and implement them automatically with JPA.
@PropertySource("classpath:db-config.properties") // Properties to configure the DataSource
public class PlayerConfiguration {

    protected Logger log;
    protected DataSource dataSource;

    public PlayerConfiguration() { log = Logger.getLogger(getClass().getName());}


    /**
     * Creates a datasource pointing to the production database.
     */
    @Bean
    @Profile("!test")
    public DataSource dataSource(){
        log.info("dataSource() invoked");

        //TODO Update the datasource to use something like PSQL instead of an embedded test DB (inside of config file).
        dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:test-player-db/schema.sql")
                .addScript("classpath:test-player-db/data.sql").build();

        log.info("dataSource = " + dataSource);

        // Sanity check
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> players = jdbcTemplate.queryForList("SELECT id FROM T_PLAYER");
        log.info("System has " + players.size() + " accounts");

        return dataSource;
    }


    /**
     * Creates and in-memory testing dtabase populated with test data for quick testing.
     */
    @Bean
    @Profile("test")
    public DataSource testDataSource(){
        log.info("TEST dataSource() invoked");

        //TODO Update the datasource to use something like PSQL instead of an embedded test DB.
        dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:test-player-db/schema.sql")
                .addScript("classpath:test-player-db/data.sql").build();

        log.info("dataSource = " + dataSource);

        // Sanity check
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> players = jdbcTemplate.queryForList("SELECT id FROM T_PLAYER");
        log.info("System has " + players.size() + " accounts");

        return dataSource;
    }
}
