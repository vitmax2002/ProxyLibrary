package com.esempla.proxy.service;

import com.esempla.proxy.model.AuthenticationResponse;
import com.esempla.proxy.model.Token;
import com.esempla.proxy.model.dto.AuthenticationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {
    private final RestTemplate restTemplate;
    @Value("${name.service.url}")
    private String paymentsServiceUrl;

    public AuthenticationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void authenticate(AuthenticationDto authDto) {
        String uriAuth = paymentsServiceUrl + "/user/authenticate";
        HttpHeaders headersAuth = new HttpHeaders();
        headersAuth.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthenticationDto> requestEntity = new HttpEntity<>(authDto, headersAuth);
        ResponseEntity<AuthenticationResponse> responseEntity = restTemplate.exchange(uriAuth, HttpMethod.POST, requestEntity, AuthenticationResponse.class);
        Token.token= responseEntity.getBody().getToken();
    }
}
