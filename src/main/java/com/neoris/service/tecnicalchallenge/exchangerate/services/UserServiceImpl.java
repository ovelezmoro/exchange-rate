package com.neoris.service.tecnicalchallenge.exchangerate.services;

import com.neoris.service.tecnicalchallenge.security.SecurityUserDetail;
import com.neoris.service.tecnicalchallenge.exchangerate.models.RoleModel;
import com.neoris.service.tecnicalchallenge.exchangerate.models.UserModel;
import com.neoris.service.tecnicalchallenge.exchangerate.repositories.UserRepository;
import com.neoris.service.tecnicalchallenge.model.AuthRequest;
import com.neoris.service.tecnicalchallenge.model.MessageResponse;
import com.neoris.service.tecnicalchallenge.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Mono<SecurityUserDetail> validUser(AuthRequest request) {
        return Mono.just(userRepository.findOneByEmail(request.getEmail()))
                .filter(model -> passwordEncoder.matches(request.getPassword(), model.getPassword()))
                .map(model -> SecurityUserDetail.builder()
                        .username(model.getEmail())
                        .password(model.getPassword())
                        .enabled(model.getEnabled())
                        .roles(model.getRoles().stream().map(RoleModel::getName).collect(Collectors.toList()))
                        .build())
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<MessageResponse> create(Mono<UserRequest> request) {
        return request.filter(userRequest -> userRepository.findOneByEmail(userRequest.getEmail()) != null)
                .flatMap(userRequest -> {
                    userRepository.save(
                            UserModel.builder().enabled(true).email(userRequest.getEmail())
                                    .password(passwordEncoder.encode(userRequest.getPassword()))
                                    .build());
                    return Mono.just(new MessageResponse().message("Usuario registrado exitomsamente"));
                });
    }
}
