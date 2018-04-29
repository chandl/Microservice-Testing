package com.cseverson.cryptomals.market_service.service;

import com.cseverson.cryptomals.market_service.model.MarketConfiguration;
import com.cseverson.cryptomals.market_service.model.cryptocoin.CryptoCoinRepository;
import com.cseverson.cryptomals.market_service.model.pricehistory.PriceHistoryRepository;
import com.cseverson.cryptomals.market_service.model.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.util.logging.Logger;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(MarketConfiguration.class)
public class MarketService {

    @Autowired
    protected PriceHistoryRepository priceHistoryRepository;

    @Autowired
    protected CryptoCoinRepository cryptoCoinRepository;

    @Autowired
    protected TransactionRepository transactionRepository;

    protected Logger log = Logger.getLogger(MarketService.class.getName());

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "market_Service/market-service");
        SpringApplication.run(MarketService.class, args);
    }

}
