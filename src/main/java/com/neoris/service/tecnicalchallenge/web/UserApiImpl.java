package com.neoris.service.tecnicalchallenge.web;


import com.neoris.service.tecnicalchallenge.api.UserApiDelegate;
import com.neoris.service.tecnicalchallenge.exchangerate.dto.Role;
import com.neoris.service.tecnicalchallenge.exchangerate.dto.User;
import com.neoris.service.tecnicalchallenge.exchangerate.models.RoleModel;
import com.neoris.service.tecnicalchallenge.exchangerate.models.UserModel;
import com.neoris.service.tecnicalchallenge.exchangerate.services.UserService;
import com.neoris.service.tecnicalchallenge.model.AuthRequest;
import com.neoris.service.tecnicalchallenge.model.AuthResponse;
import com.neoris.service.tecnicalchallenge.model.MessageResponse;
import com.neoris.service.tecnicalchallenge.model.UserRequest;
import com.neoris.service.tecnicalchallenge.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class UserApiImpl implements UserApiDelegate {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    public Mono<ResponseEntity<AuthResponse>> loginUser(Mono<AuthRequest> authRequest, ServerWebExchange exchange) {
        return authRequest.flatMap(request -> userService.validUser(request))
                .map(userDetails -> ResponseEntity.ok(new AuthResponse().token(jwtUtil.generateToken(userDetails))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Override
    public Mono<ResponseEntity<MessageResponse>> createUserPOST(Mono<UserRequest> userRequest, ServerWebExchange exchange) {
        return UserApiDelegate.super.createUserPOST(userRequest, exchange);
    }
}
