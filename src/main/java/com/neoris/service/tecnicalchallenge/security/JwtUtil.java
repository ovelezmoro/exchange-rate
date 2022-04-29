/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoris.service.tecnicalchallenge.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoris.service.tecnicalchallenge.util.constants.ExchangeRateConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;


@Getter
@Service
public class JwtUtil {

    public static String generateToken(SecurityUserDetail userDetail) {
        return Jwts.builder()
                .claim("authorities", userDetail.getAuthorities())
                .setSubject(userDetail.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ExchangeRateConstants.EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS512, ExchangeRateConstants.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).compact();    }

    public static String create(Authentication authentication) throws IOException {
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
        String jsonInString = new ObjectMapper().writeValueAsString(authentication.getPrincipal());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(jsonInString)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ExchangeRateConstants.EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS512, ExchangeRateConstants.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).compact();

    }

    public static JwtStatus validate(String token) {
        getClaims(token);
        return new JwtStatus(true, "token valido");
    }

    public static Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(ExchangeRateConstants.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(resolve(token)).getBody();
    }

    public static UserActiveDirectory getPrincipal(String token) {
        try {
            String subjet = getClaims(token).getSubject();
            return new ObjectMapper().readValue(subjet, UserActiveDirectory.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public static Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {

        Object roles = getClaims(token).get("authorities");

        return Arrays
                .asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                        .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

    }

    public static String resolve(String token) {
        if (token != null && token.startsWith(ExchangeRateConstants.TOKEN_PREFIX)) {
            return token.replace(ExchangeRateConstants.TOKEN_PREFIX, "");
        }
        return token;
    }

}
