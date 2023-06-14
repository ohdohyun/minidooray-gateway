package com.nhn.sadari.minidooray.gateway.service;

import com.nhn.sadari.minidooray.gateway.domain.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.account.*;
import com.nhn.sadari.minidooray.gateway.domain.common.CommonResponse;
import com.nhn.sadari.minidooray.gateway.enumclass.MemberStatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;


class AccountServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    void registerAccount() {
        String testLoginId = "testId";
        String testPassword = "testPassword";
        String testEmail = "testEmail";
        String testName = "testName";
        Long expectedId = 1L;
        String requestUrl = "http://localhost:7070/api/accounts";
        AccountRegister accountRegister = new AccountRegister(testLoginId, testPassword, testEmail, testName);

        CommonResponse<IdDto> response = new CommonResponse<>(
                new CommonResponse.Header(true, 201, "success")
                ,
                List.of(new IdDto(expectedId)),
                1
        );

        given(restTemplate.exchange(
                        eq(requestUrl),
                        eq(HttpMethod.POST),
                        any(HttpEntity.class),
                        eq(new ParameterizedTypeReference<CommonResponse<IdDto>>() {
                        })
                )
        ).willReturn(ResponseEntity.ok(response));

        IdDto idDto = accountService.registerAccount(accountRegister);
        assertThat(idDto.getId()).isEqualTo(expectedId);
    }


    @Test
    void deleteAccount() {
        Long requestId = 1L;
        String requestUrl = "http://localhost:7070/api/accounts/" + requestId;

        CommonResponse<IdDto> response = new CommonResponse<>(
                new CommonResponse.Header(true, 200, "success")
                ,
                List.of(new IdDto(requestId)),
                1
        );

        given(restTemplate.exchange(
                        eq(requestUrl),
                        eq(HttpMethod.DELETE),
                        any(HttpEntity.class),
                        eq(new ParameterizedTypeReference<CommonResponse<IdDto>>() {
                        })
                )
        ).willReturn(ResponseEntity.ok(response));

        IdDto idDto = accountService.deleteAccount(requestId);
        assertThat(idDto.getId()).isEqualTo(requestId);

    }

    @Test
    void getAccountInfo() {

        //given
        String testLoginId = "testId";
        String testPassword = "testPassword";
        String testEmail = "testEmail";
        String testName = "testName";
        Long requestId = 1L;
        String requestUrl = "http://localhost:7070/api/accounts/" + requestId;

        CommonResponse<AccountInfo> response = new CommonResponse<>(
                new CommonResponse.Header(true, 200, "success"),
                List.of(new AccountInfo(testLoginId, testPassword, testEmail, testName, MemberStatusType.가입)),
                1
        );

        given(restTemplate.exchange(
                        eq(requestUrl),
                        eq(HttpMethod.GET),
                        any(HttpEntity.class),
                        eq(new ParameterizedTypeReference<CommonResponse<AccountInfo>>() {
                        })
                )
        ).willReturn(ResponseEntity.ok(response));

        // when
        AccountInfo result = accountService.getAccountInfo(requestId);
        // then

        assertThat(result.getLoginId()).isEqualTo(testLoginId);
        assertThat(result.getName()).isEqualTo(testName);
        assertThat(result.getStatus()).isEqualTo(MemberStatusType.가입);
    }

    @Test
    void getAccountList() {
        //given
        String requestUrl = "http://localhost:7070/api/accounts";

        CommonResponse<AccountListDto> response = new CommonResponse<>(
                new CommonResponse.Header(true, 200, "success"),
                List.of(new AccountListDto(1L, "test1", "testEmail@1")),
                1
        );

        given(restTemplate.exchange(
                        eq(requestUrl),
                        eq(HttpMethod.GET),
                        any(HttpEntity.class),
                        eq(new ParameterizedTypeReference<CommonResponse<AccountListDto>>(){

                        }))
        ).willReturn(ResponseEntity.ok(response));

        // when
        List<AccountListDto> result = accountService.getAccountList();
        // then
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void doAccountUpdate() {
        Long accountId = 1L;
        AccountInfo accountUpdate = new AccountInfo("testId", "testPassword", "testEmail", "testName", MemberStatusType.가입);
        CommonResponse<IdDto> response = new CommonResponse<>(
                new CommonResponse.Header(true, 200, "success"),
                List.of(new IdDto(accountId)),
                1
        );

        given(restTemplate.exchange(
                eq("http://localhost:7070/api/accounts/" + accountId),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<CommonResponse<IdDto>>() {
                }))
        ).willReturn(ResponseEntity.ok(response));

        IdDto idDto = accountService.doAccountUpdate(accountUpdate, accountId);

        assertThat(idDto).isNotNull();
        assertThat(idDto.getId()).isEqualTo(accountId);
    }

    @Test
    void getAccountRedis() {
        // given
        String testLoginId = "testLoginId";
        String requestUrl = "http://localhost:7070/api/auth/" + testLoginId;

        CommonResponse<AccountRedis> response = new CommonResponse<>(
                new CommonResponse.Header(true, 200, "success"),
                List.of(new AccountRedis(1L,testLoginId, "testPassword", MemberStatusType.가입)),
                1
        );

        given(restTemplate.exchange(
                        eq(requestUrl),
                        eq(HttpMethod.GET),
                        any(HttpEntity.class),
                        eq(new ParameterizedTypeReference<CommonResponse<AccountRedis>>() {})
                )
        ).willReturn(ResponseEntity.ok(response));

        // when
        AccountRedis result = accountService.getAccountRedis(testLoginId);

        // then
        assertThat(result.getLoginId()).isEqualTo(testLoginId);
    }


    @Test
    void getLoginInfo() {
        // given
        String testLoginId = "testLoginId";
        String testPassword = "testPassword";
        String requestUrl = "http://localhost:7070/api/login?loginId=" + testLoginId;

        CommonResponse<LoginRequest> response = new CommonResponse<>(
                new CommonResponse.Header(true, 200, "success"),
                List.of(new LoginRequest(testLoginId, testPassword)),
                1
        );

        given(restTemplate.exchange(
                        eq(requestUrl),
                        eq(HttpMethod.GET),
                        any(HttpEntity.class),
                        eq(new ParameterizedTypeReference<CommonResponse<LoginRequest>>() {})
                )
        ).willReturn(ResponseEntity.ok(response));

        // when
        LoginRequest result = accountService.getLoginInfo(testLoginId);

        // then
        assertThat(result.getLoginId()).isEqualTo(testLoginId);
        assertThat(result.getPassword()).isEqualTo(testPassword);
    }

}