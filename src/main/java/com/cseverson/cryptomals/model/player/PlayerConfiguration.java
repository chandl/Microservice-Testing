package com.cseverson.cryptomals.model.player;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

//@SpringBootApplication // Combines EnableAutoCOnfiguration, Configuration, and ComponentScan annotations
@Configuration
@ComponentScan(basePackages = {"com.cseverson.cryptomals.model.player", "com.cseverson.cryptomals.controller.player"})
@EntityScan("com.cseverson.cryptomals.model.player") //Specify where JPA @Entity classes are
@EnableJpaRepositories("com.cseverson.cryptomals.model.player") // Look for classes extending Spring Data's 'Repository marker interface and implement them automatically with JPA.
@PropertySource("classpath:db-config.properties") // Properties to configure the DataSource
public class PlayerConfiguration {

    protected Logger log;

    public PlayerConfiguration() { log = Logger.getLogger(getClass().getName());}

    @Bean
    public DataSource dataSource(){
        log.info("dataSource() invoked");

        DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdb/schema.sql")
                .addScript("classpath:testdb/data.sql").build();

        log.info("dataSource = " + dataSource);

        // Sanity check
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> players = jdbcTemplate.queryForList("SELECT id FROM T_PLAYER");
        log.info("System has " + players.size() + " accounts");

        return dataSource;
    }


}
