package com.neoris.service.tecnicalchallenge.exchangerate.services;

import com.neoris.service.tecnicalchallenge.security.SecurityUserDetail;
import com.neoris.service.tecnicalchallenge.model.AuthRequest;
import com.neoris.service.tecnicalchallenge.model.MessageResponse;
import com.neoris.service.tecnicalchallenge.model.UserRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public interface UserService {

    Mono<SecurityUserDetail> validUser(AuthRequest request);

    Mono<MessageResponse> create(Mono<UserRequest> request);

}
