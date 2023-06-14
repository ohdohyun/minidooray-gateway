package com.nhn.sadari.minidooray.gateway.service;

import com.nhn.sadari.minidooray.gateway.domain.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.account.*;
import com.nhn.sadari.minidooray.gateway.domain.common.CommonResponse;
import com.nhn.sadari.minidooray.gateway.exception.AlreadyExistsException;
import com.nhn.sadari.minidooray.gateway.exception.NotFoundException;
import com.nhn.sadari.minidooray.gateway.exception.UserUpdateFailException;
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


    // 회원가입
    public IdDto registerAccount(AccountRegister accountRegister) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        accountRegister.encodePassword(passwordEncoder.encode(accountRegister.getPassword()));

        HttpEntity<AccountRegister> requestEntity = new HttpEntity<>(accountRegister, httpHeaders);
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        CommonResponse<?> response = exchange.getBody();

        // 생성 했으면 return
        if (201 != response.getHeader().getResultCode()) {
            throw new AlreadyExistsException(response.getHeader().getResultMessage());
        }
        return (IdDto) response.getResult().get(0);
    }


    // 회원탈퇴
    public IdDto deleteAccount(Long accountId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts/" + accountId,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        CommonResponse<?> response = exchange.getBody();

        // 생성 했으면 return
        if (200 != response.getHeader().getResultCode()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        }
        return (IdDto) response.getResult().get(0);
    }

    // 회원 정보 정보 요청
    public AccountInfo getAccountInfo(Long accountId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<AccountInfo>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts/" + accountId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        // 예외
        CommonResponse<?> response = exchange.getBody();

        if (200 != response.getHeader().getResultCode()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        }

        return (AccountInfo) response.getResult().get(0);
    }

    public List<AccountListDto> getAccountList() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<AccountListDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts/group",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        CommonResponse<?> response = exchange.getBody();

        if (200 != response.getHeader().getResultCode()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        }
        return (List<AccountListDto>) response.getResult();




    }

    // 회원정보 수정
    public IdDto doAccountUpdate(AccountInfo accountUpdate, Long accountId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<AccountInfo> requestEntity = new HttpEntity<>(accountUpdate, httpHeaders);
        ResponseEntity<CommonResponse<IdDto>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/accounts/modify/" + accountId,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        CommonResponse<?> response = exchange.getBody();

        if (200 != response.getHeader().getResultCode()) {
            throw new UserUpdateFailException(response.getHeader().getResultMessage());
        }
        return (IdDto) response.getResult().get(0);
    }

    public AccountRedis getAccountRedis(String loginId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<AccountRedis>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/auth/" + loginId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });


        // 여기도 예외처리.
        CommonResponse<?> response = exchange.getBody();

        if (404 == response.getHeader().getResultCode()) {
            return new AccountRedis();
        } else if (200!= response.getHeader().getResultCode()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        } else {
            return (AccountRedis) response.getResult().get(0);
        }
    }

    public LoginRequest getLoginInfo(String loginId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<CommonResponse<LoginRequest>> exchange = restTemplate.exchange("http://" + "localhost" + ":" + "7070" + "/api/login?loginId=" + loginId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        CommonResponse<?> response = exchange.getBody();

        if (200 != response.getHeader().getResultCode()) {
            throw new NotFoundException(response.getHeader().getResultMessage());
        }
        return (LoginRequest) response.getResult().get(0);
    }

}
