package com.esempla.proxy.controller;

import com.esempla.proxy.model.dto.AuthenticationDto;
import com.esempla.proxy.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/auth")
    public ResponseEntity<Void> authenticate(@RequestBody AuthenticationDto authenticationDto){
        authenticationService.authenticate(authenticationDto);
        return ResponseEntity.noContent().build();
    }
}
