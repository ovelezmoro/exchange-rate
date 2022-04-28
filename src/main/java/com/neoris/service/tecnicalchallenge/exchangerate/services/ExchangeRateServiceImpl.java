package com.neoris.service.tecnicalchallenge.exchangerate.services;

import com.neoris.service.tecnicalchallenge.exchangerate.repositories.ExchangeRateRepository;
import com.neoris.service.tecnicalchallenge.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.dateToString;
import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.stringToDate;
import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.FORMAT_DATE;
import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.ENG_FORMAT_DATE;
import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.HRS_FORMAT_DATE;
import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.DB_FORMAT_DATE;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Override
    public ResponseEntity<ExchangeRateResponse> create(ExchangeRate rate) {
        return null;
    }

    @Override
    public Mono<ExchangeRateResponse> find(String from, String to) {
        return Mono.justOrEmpty(exchangeRateRepository.findOneByBaseCurrencyAndExchangeCurrencyAndDay(from, to, dateToString(new Date(), "ddMMyyyy")))
                .map(exchangeRate -> new ExchangeRateResponse()
                        .base(exchangeRate.getBaseCurrency())
                        .result(new ExchangeRate()
                                .currencyCode(exchangeRate.getExchangeCurrency())
                                .amount(exchangeRate.getRateAmount()))
                        .updated(dateToString(new Date(), FORMAT_DATE)));
    }

    @Override
    public Mono<HistoryExchangeRateResponse> history(String date, String from) {
        String day = dateToString(stringToDate(date, ENG_FORMAT_DATE), DB_FORMAT_DATE) ;
        return Flux.fromIterable(exchangeRateRepository.findAllByBaseCurrencyAndDay(from, day))
                .map(exchangeRate -> new ExchangeRate()
                        .currencyCode(exchangeRate.getExchangeCurrency())
                        .amount(exchangeRate.getRateAmount()))
                .collectList()
                .flatMap(exchangeRates -> Mono.just(new HistoryExchangeRateResponse().base(from)
                        .result(exchangeRates)
                        .updated(dateToString(new Date(), HRS_FORMAT_DATE))));
    }

    @Override
    public Mono<ConvertExchangeRateResponse> convert(ConvertExchangeRateRequest request) {
        return Mono.just(exchangeRateRepository.findOneByBaseCurrencyAndExchangeCurrencyAndDay(
                        request.getFrom(),
                        request.getTo(), dateToString(new Date(), "ddMMyyyy")))
            .map(exchangeRate -> new ConvertExchangeRateResponse()
                    .updated(dateToString(new Date(), HRS_FORMAT_DATE))
                    .base(exchangeRate.getBaseCurrency())
                    .amount(request.getAmount())
                    .putResultItem(exchangeRate.getExchangeCurrency(), exchangeRate.getRateAmount() * request.getAmount())
                    .putResultItem("rate", exchangeRate.getRateAmount())
            );

    }
}
