package com.neoris.service.tecnicalchallenge.web;

import com.neoris.service.tecnicalchallenge.api.ExchangeRateApiDelegate;
import com.neoris.service.tecnicalchallenge.model.EchangeRateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ExchangeRateApi implements ExchangeRateApiDelegate {

    @Override
    public Mono<ResponseEntity<EchangeRateResponse>> findExchangeRateGET(String from, String to, ServerWebExchange exchange) {
        return ExchangeRateApiDelegate.super.findExchangeRateGET(from, to, exchange);
    }
}
