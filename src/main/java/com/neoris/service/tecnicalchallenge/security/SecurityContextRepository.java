package com.neoris.service.tecnicalchallenge.security;

import com.google.common.net.HttpHeaders;
import com.neoris.service.tecnicalchallenge.util.constants.ExchangeRateConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Slf4j
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .flatMap(authHeader -> {
                    String authToken = authHeader.replace(ExchangeRateConstants.TOKEN_PREFIX, "");
                    return authenticationManager
                            .authenticate(new UsernamePasswordAuthenticationToken(authToken, authToken))
                            .map(SecurityContextImpl::new);
                });
    }

}
