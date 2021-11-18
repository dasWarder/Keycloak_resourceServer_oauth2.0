package com.example.resourceserver.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/tokens")
public class TokenController {


    @GetMapping
    public ResponseEntity<JwtInfo> getToken(@AuthenticationPrincipal Jwt principal) {

        Map<String, Object> claims = principal.getClaims();
        Map<String, Object> headers = principal.getHeaders();

        JwtInfo jwtInfo = new JwtInfo(claims, headers);

        return ResponseEntity.ok(jwtInfo);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class JwtInfo {

        private Map<String, Object> claims;

        private Map<String, Object> headers;
    }
}
