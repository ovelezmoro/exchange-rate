package com.neoris.service.tecnicalchallenge.config;

import com.neoris.service.tecnicalchallenge.exchangerate.models.ExchangeRateModel;
import com.neoris.service.tecnicalchallenge.exchangerate.repositories.ExchangeRateRepository;
import com.neoris.service.tecnicalchallenge.exchangerate.repositories.UserRepository;
import com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
public class InitializeDataSourceConfig implements CommandLineRunner {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args)  {
        log.info("Eliminando Data de Prueba");
        exchangeRateRepository.deleteAll();
        log.info("Registrando Data de Prueba");

        List<ExchangeRateModel> exchangeRateModelList = List.of(
                // 25/04/2022
                ExchangeRateModel.builder().day("25042022").baseCurrency("USD").exchangeCurrency("PEN").rateAmount(3.76976).build(),
                ExchangeRateModel.builder().day("25042022").baseCurrency("PEN").exchangeCurrency("USD").rateAmount(0.26527).build(),
                ExchangeRateModel.builder().day("25042022").baseCurrency("USD").exchangeCurrency("EUR").rateAmount(0.93362).build(),
                ExchangeRateModel.builder().day("25042022").baseCurrency("EUR").exchangeCurrency("USD").rateAmount(1.0711).build(),
                //26/04/2022
                ExchangeRateModel.builder().day("26042022").baseCurrency("USD").exchangeCurrency("PEN").rateAmount(3.80853).build(),
                ExchangeRateModel.builder().day("26042022").baseCurrency("PEN").exchangeCurrency("USD").rateAmount(0.26257).build(),
                ExchangeRateModel.builder().day("26042022").baseCurrency("USD").exchangeCurrency("EUR").rateAmount(0.93362).build(),
                ExchangeRateModel.builder().day("26042022").baseCurrency("EUR").exchangeCurrency("USD").rateAmount(0.93859).build(),
                //27/04/2022
                ExchangeRateModel.builder().day("27042022").baseCurrency("USD").exchangeCurrency("PEN").rateAmount(3.82596).build(),
                ExchangeRateModel.builder().day("27042022").baseCurrency("PEN").exchangeCurrency("USD").rateAmount(0.26137).build(),
                ExchangeRateModel.builder().day("27042022").baseCurrency("USD").exchangeCurrency("EUR").rateAmount(0.94934).build(),
                ExchangeRateModel.builder().day("27042022").baseCurrency("EUR").exchangeCurrency("USD").rateAmount(1.05337).build(),
                //Hoy
                ExchangeRateModel.builder().day(ExchangeRateUtil.dateToString(new Date(), "ddMMyyyy")).baseCurrency("USD").exchangeCurrency("PEN").rateAmount(3.82376).build(),
                ExchangeRateModel.builder().day(ExchangeRateUtil.dateToString(new Date(), "ddMMyyyy")).baseCurrency("PEN").exchangeCurrency("USD").rateAmount(0.26148).build(),
                ExchangeRateModel.builder().day(ExchangeRateUtil.dateToString(new Date(), "ddMMyyyy")).baseCurrency("USD").exchangeCurrency("EUR").rateAmount(0.95182).build(),
                ExchangeRateModel.builder().day(ExchangeRateUtil.dateToString(new Date(), "ddMMyyyy")).baseCurrency("EUR").exchangeCurrency("USD").rateAmount(1.05074).build()

                //ExchangeRate.builder().day("25042022").baseCurrency("USD").exchangeCurrency("MXN").rateAmount(20.31475).build(),
                //ExchangeRate.builder().day("25042022").baseCurrency("USD").exchangeCurrency("JPY").rateAmount(127.93103).build(),
                //ExchangeRate.builder().day("25042022").baseCurrency("USD").exchangeCurrency("ARS").rateAmount(114.71841).build(),
        );
        exchangeRateRepository.saveAll(exchangeRateModelList);
        log.info("Termino de registro de Data de Prueba");
    }
}
