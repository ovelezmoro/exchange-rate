package com.neoris.service.tecnicalchallenge.exchangerate.services;

import com.neoris.service.tecnicalchallenge.model.*;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface ExchangeRateService {

    ResponseEntity<ExchangeRateResponse> create(ExchangeRate rate);

    Mono<ExchangeRateResponse> find(String from, String to);

    Mono<HistoryExchangeRateResponse> history(String date, String from);

    Mono<ConvertExchangeRateResponse> convert(ConvertExchangeRateRequest convertExchangeRateRequest);

}
