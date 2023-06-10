package com.nhn.sadari.minidooray.gateway.service;

import com.nhn.sadari.minidooray.gateway.domain.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.account.AccountRegister;
import com.nhn.sadari.minidooray.gateway.domain.account.AccountModify;
import com.nhn.sadari.minidooray.gateway.domain.account.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;

    public IdDto registerAccount(AccountRegister accountRegister) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        accountRegister.setPassword(passwordEncoder.encode(accountRegister.getPassword()));

        HttpEntity<AccountRegister> requestEntity = new HttpEntity<>(accountRegister, httpHeaders);
        ResponseEntity<IdDto> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    public IdDto deleteAccount(Long accountId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<IdDto> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts/" + accountId,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    public AccountModify getAccountUpdate(Long accountId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));


        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<AccountModify> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts/modify/" + accountId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    public IdDto doAccountUpdate(AccountModify accountUpdate, Long accountId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<AccountModify> requestEntity = new HttpEntity<>(accountUpdate, httpHeaders);
        ResponseEntity<IdDto> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts/modify/" + accountId,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    public LoginRequest getAccountInfo(String loginId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<LoginRequest> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts?loginId=" + loginId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }
}
