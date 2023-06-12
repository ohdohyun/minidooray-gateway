package com.nhn.sadari.minidooray.gateway.service;

import com.nhn.sadari.minidooray.gateway.domain.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.account.AccountRedis;
import com.nhn.sadari.minidooray.gateway.domain.account.AccountRegister;
import com.nhn.sadari.minidooray.gateway.domain.account.AccountInfo;
import com.nhn.sadari.minidooray.gateway.domain.account.LoginRequest;
import com.nhn.sadari.minidooray.gateway.domain.common.CommonResponse;
import com.nhn.sadari.minidooray.gateway.exception.UserAlreadyException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        CommonResponse response = exchange.getBody();

        // 생성 했으면 return
        if (201 != response.getHeader().getResultCode()) {
            //#TODO 익셉션 만들고 수정
            throw new UserAlreadyException(response.getHeader().getResultMessage());
        }
        return (IdDto) response.getResult().get(0);
    }

    public CommonResponse<IdDto> deleteAccount(Long accountId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts/" + accountId,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });


        return exchange.getBody();
    }

    public AccountInfo getAccountUpdate(Long accountId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));


        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<AccountInfo>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts/" + accountId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        // 예외
        CommonResponse response = exchange.getBody();

        if (200 != response.getHeader().getResultCode()) {
            throw new UsernameNotFoundException(response.getHeader().getResultMessage());
        }

        return exchange.getBody().getResult().get(0);
    }

    public CommonResponse<IdDto> doAccountUpdate(AccountInfo accountUpdate, Long accountId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<AccountInfo> requestEntity = new HttpEntity<>(accountUpdate, httpHeaders);
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts/modify/" + accountId,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    public AccountRedis getAccountRedis(String loginId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<AccountRedis>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/auth/" + loginId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });


        // 여기도 예외처리.
        CommonResponse response = exchange.getBody();

        if (404 == response.getHeader().getResultCode()) {
            return new AccountRedis();
        } else {
            return (AccountRedis) response.getResult().get(0);
        }
    }

    public LoginRequest getLoginInfo(String loginId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<LoginRequest>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/login?loginId=" + loginId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        // #TODO null 고치기
        return (LoginRequest) exchange.getBody().getResult().get(0);
    }

}
