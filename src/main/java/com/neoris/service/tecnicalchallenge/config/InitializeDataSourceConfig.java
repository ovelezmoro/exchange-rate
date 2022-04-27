package com.neoris.service.tecnicalchallenge.config;

import com.neoris.service.tecnicalchallenge.exchangerate.models.ExchangeRate;
import com.neoris.service.tecnicalchallenge.exchangerate.repositories.ExchangeRateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class InitializeDataSourceConfig implements CommandLineRunner {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Eliminando Data de Prueba");
        exchangeRateRepository.deleteAll();
        log.info("Registrando Data de Prueba");
        List<ExchangeRate> exchangeRateList = List.of(
            ExchangeRate.builder().baseCurrency("USD").exchangeCurrency("PEN").rateAmount(3.82395).build(),
            ExchangeRate.builder().baseCurrency("USD").exchangeCurrency("EUR").rateAmount(0.93931).build(),
            ExchangeRate.builder().baseCurrency("USD").exchangeCurrency("MXN").rateAmount(20.33706).build(),
            ExchangeRate.builder().baseCurrency("USD").exchangeCurrency("JPY").rateAmount(127.51442).build(),
            ExchangeRate.builder().baseCurrency("USD").exchangeCurrency("ARS").rateAmount(114.82909).build()
        );
        exchangeRateRepository.saveAll(exchangeRateList);
        log.info("Termino de registro de Data de Prueba");
    }
}
