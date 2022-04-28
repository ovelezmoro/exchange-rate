package com.neoris.service.tecnicalchallenge.web;


import com.neoris.service.tecnicalchallenge.api.ExchangeRateApiDelegate;
import com.neoris.service.tecnicalchallenge.exchangerate.services.ExchangeRateService;
import com.neoris.service.tecnicalchallenge.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ExchangeRateApiImpl implements ExchangeRateApiDelegate {

    @Autowired
    private ExchangeRateService exchangeRateService;


    @Override
    public Mono<ResponseEntity<ExchangeRateResponse>> createExchangeRatePOST(Mono<ExchangeRate> body, ServerWebExchange exchange) {
        return ExchangeRateApiDelegate.super.createExchangeRatePOST(body, exchange);
    }

    @Override
    public Mono<ResponseEntity<ExchangeRateResponse>> exchangeRateGET(String from, String to, ServerWebExchange exchange) {
        return exchangeRateService.find(from, to)
                .flatMap(exchangeRateResponse -> Mono.just(ResponseEntity.ok(exchangeRateResponse)));
    }

    @Override
    public Mono<ResponseEntity<HistoryExchangeRateResponse>> historyExchangeRateGET(String date, String from, ServerWebExchange exchange) {
        return exchangeRateService.history(date, from)
                .flatMap(historyExchangeRateResponse -> Mono.just(ResponseEntity.ok(historyExchangeRateResponse)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<ConvertExchangeRateResponse>> convertExchangeRatePOST(Mono<ConvertExchangeRateRequest> convertExchangeRateRequest, ServerWebExchange exchange) {
        return convertExchangeRateRequest
                .flatMap(request -> exchangeRateService.convert(request))
                .flatMap(convertResponse -> Mono.just(ResponseEntity.ok(convertResponse)));
    }
}
