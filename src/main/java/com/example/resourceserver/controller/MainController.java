package com.example.resourceserver.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class MainController {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class UserInfo {

        private String firstName;

        private String lastName;

        private String userId;
    }


    @GetMapping("/check")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Pong");
    }


    @Secured("ROLE_DEVELOPER")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {

        log.info("Run deleteUser endpoint for user with ID = {}", id);

        return ResponseEntity.ok("Delete a user with id = " + id);
    }

    @PreAuthorize("hasRole('ADMIN') or #id == #principal.subject")
    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable("id") String id, @AuthenticationPrincipal Jwt principal) {

        log.info("Run getUserById endpoint for a user with ID = {}", id);

        return ResponseEntity.ok("Fetch a user with id = " + id + "and JWT subject = " + principal.getSubject());
    }

    @GetMapping("/user/{id}")
    @PostAuthorize("returnObject.body.userId == #principal.subject")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable("id") String id, @AuthenticationPrincipal Jwt principal) {

        log.info("Run getUserById endpoint for a user with ID = {}", id);
        UserInfo userInfo = new UserInfo("Alex", "Petrov", id);

        return ResponseEntity.ok(userInfo);
    }



}
