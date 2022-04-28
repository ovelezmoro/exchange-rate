package com.neoris.service.tecnicalchallenge.exchangerate.repositories;

import com.neoris.service.tecnicalchallenge.exchangerate.models.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {


    ExchangeRate findOneByBaseCurrencyAndExchangeCurrencyAndDay(String baseCurrency,
                                                                  String exchangeCurrency,
                                                                  String today);

    List<ExchangeRate> findAllByBaseCurrencyAndDay(String baseCurrency, String day);


}
