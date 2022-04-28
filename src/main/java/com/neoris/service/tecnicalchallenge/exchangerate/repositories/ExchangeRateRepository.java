package com.neoris.service.tecnicalchallenge.exchangerate.repositories;

import com.neoris.service.tecnicalchallenge.exchangerate.models.ExchangeRateModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateModel, Integer> {


    ExchangeRateModel findOneByBaseCurrencyAndExchangeCurrencyAndDay(String baseCurrency,
                                                                     String exchangeCurrency,
                                                                     String today);

    List<ExchangeRateModel> findAllByBaseCurrencyAndDay(String baseCurrency, String day);


}
