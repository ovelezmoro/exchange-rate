package com.neoris.service.tecnicalchallenge.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtStatus {

    private boolean valid;
    private String message;

}
