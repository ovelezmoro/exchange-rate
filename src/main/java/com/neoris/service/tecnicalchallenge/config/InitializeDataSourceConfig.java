package com.neoris.service.tecnicalchallenge.config;

import com.neoris.service.tecnicalchallenge.exchangerate.models.ExchangeRate;
import com.neoris.service.tecnicalchallenge.exchangerate.repositories.ExchangeRateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.dateToString;
import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.stringToDate;
import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.FORMAT_DATE;

@Slf4j
@Configuration
public class InitializeDataSourceConfig implements CommandLineRunner {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Override
    public void run(String... args)  {
        log.info("Eliminando Data de Prueba");
        exchangeRateRepository.deleteAll();
        log.info("Registrando Data de Prueba");
        List<ExchangeRate> exchangeRateList = List.of(
// 25/04/2022
                ExchangeRate.builder().day("25042022").baseCurrency("USD").exchangeCurrency("PEN").rateAmount(3.76976).build(),
                ExchangeRate.builder().day("25042022").baseCurrency("USD").exchangeCurrency("EUR").rateAmount(0.93362).build(),
                //ExchangeRate.builder().day("25042022").baseCurrency("USD").exchangeCurrency("MXN").rateAmount(20.31475).build(),
                //ExchangeRate.builder().day("25042022").baseCurrency("USD").exchangeCurrency("JPY").rateAmount(127.93103).build(),
                //ExchangeRate.builder().day("25042022").baseCurrency("USD").exchangeCurrency("ARS").rateAmount(114.71841).build(),
// 26/04/2022
                ExchangeRate.builder().day("26042022").baseCurrency("USD").exchangeCurrency("PEN").rateAmount(3.82395).build(),
                ExchangeRate.builder().day("26042022").baseCurrency("USD").exchangeCurrency("EUR").rateAmount(0.93931).build(),
                //ExchangeRate.builder().day("26042022").baseCurrency("USD").exchangeCurrency("MXN").rateAmount(20.33706).build(),
                //ExchangeRate.builder().day("26042022").baseCurrency("USD").exchangeCurrency("JPY").rateAmount(127.51442).build(),
                //ExchangeRate.builder().day("26042022").baseCurrency("USD").exchangeCurrency("ARS").rateAmount(114.82909).build(),
// 27/04/2022
                ExchangeRate.builder().day("27042022").baseCurrency("USD").exchangeCurrency("PEN").rateAmount(3.82716).build(),
                ExchangeRate.builder().day("27042022").baseCurrency("USD").exchangeCurrency("EUR").rateAmount(0.94874).build()
                //ExchangeRate.builder().day("27042022").baseCurrency("USD").exchangeCurrency("MXN").rateAmount(20.51006).build(),
                //ExchangeRate.builder().day("27042022").baseCurrency("USD").exchangeCurrency("JPY").rateAmount(127.99566).build(),
                //ExchangeRate.builder().day("27042022").baseCurrency("USD").exchangeCurrency("ARS").rateAmount(115.04569).build()
        );
        exchangeRateRepository.saveAll(exchangeRateList);
        log.info("Termino de registro de Data de Prueba");
    }
}
