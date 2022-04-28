package com.neoris.service.tecnicalchallenge.exchangerate.services;

import com.neoris.service.tecnicalchallenge.model.*;
import reactor.core.publisher.Mono;

public interface ExchangeRateService {

    Mono<ExchangeRateResponse> create(Mono<ExchangeRateRequest> request);

    Mono<ExchangeRateResponse> update(Mono<ExchangeRateRequest> request);

    Mono<ExchangeRateResponse> find(String from, String to);

    Mono<HistoryExchangeRateResponse> history(String date, String from);

    Mono<ConvertExchangeRateResponse> convert(ConvertExchangeRateRequest convertExchangeRateRequest);

}
