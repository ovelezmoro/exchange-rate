package com.neoris.service.tecnicalchallenge.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoris.service.tecnicalchallenge.util.constants.ExchangeRateConstants;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtWebFilter implements WebFilter {

    protected Mono<Void> writeErrorMessage(ServerHttpResponse response, HttpStatus status, String msg) {
        try {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper mapper=new ObjectMapper();

            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("status", status.value());
            bodyMap.put("message", msg);

            String body  = mapper.writeValueAsString(bodyMap);
            DataBuffer dataBuffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getPath().value();
        if (path.contains("login") || path.contains("signout")) {
            return chain.filter(exchange);
        }

        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null) {
            return this.writeErrorMessage(response, HttpStatus.NOT_ACCEPTABLE, "No existe Token.");
        }
        else if (!auth.startsWith(ExchangeRateConstants.TOKEN_PREFIX)) {
            return this.writeErrorMessage(response, HttpStatus.NOT_ACCEPTABLE, "Token no contiene" + ExchangeRateConstants.TOKEN_PREFIX);
        }

        String token = auth.replace(ExchangeRateConstants.TOKEN_PREFIX, StringUtil.EMPTY_STRING);
        try {
            exchange.getAttributes().put("token", token);
        } catch (Exception e) {
            return this.writeErrorMessage(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return chain.filter(exchange);
    }
}
