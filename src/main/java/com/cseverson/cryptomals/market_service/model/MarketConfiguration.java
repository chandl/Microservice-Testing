package com.cseverson.cryptomals.market_service.model;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.util.logging.Logger;

@Configuration
@ComponentScan(basePackages = {"com.cseverson.cryptomals.market_service.model", "com.cseverson.cryptomals.market_service.controller"})
@EntityScan("com.cseverson.cryptomals.market_service.model")
@EnableJpaRepositories("com.cseverson.cryptomals.market_service.model")
@PropertySource("classpath:market_service/market-db-config.properties")
public class MarketConfiguration {

    private final Logger log;
    protected DataSource dataSource;

    public MarketConfiguration() { log =  Logger.getLogger(MarketConfiguration.class.getName());}

    @Bean
    @Profile("!test")
    public DataSource dataSource(){
        log.info("dataSource() invoked");

        dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:market_service/test-market-db/schema.sql")
                .addScript("classpath:market_service/test-market-db/data.sql").build();

        log.info("dataSource = " + dataSource);

        //TODO sanity checking like in PlayerConfiguration.java

        return dataSource;
    }

    @Bean
    @Profile("test")
    public DataSource testDataSource(){
        log.info("dataSource() invoked");

//        dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:market_service/test-market-db/schema.sql")
//                .addScript("classpath:market_service/test-market-db/data.sql").build();

        dataSource = (new EmbeddedDatabaseBuilder()).build();

        log.info("dataSource = " + dataSource);

        //TODO sanity checking like in PlayerConfiguration.java

        return dataSource;
    }

}
