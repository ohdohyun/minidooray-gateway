package com.nhn.sadari.minidooray.gateway.service;

import com.nhn.sadari.minidooray.gateway.domain.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.account.AccountRegister;
import com.nhn.sadari.minidooray.gateway.domain.common.CommonResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;


class AccountServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AccountService accountService;

    public AccountServiceTest() {
        initMocks(this);
    }

    @Test
    void registerAccount() {
        String testId = "testId";
        String testPassword = "testPassword";
        String testEmail = "testEmail";
        String testName = "testName";
        Long expectedId = 1L;

        AccountRegister accountRegister = new AccountRegister(testId, testPassword, testEmail, testName);

        CommonResponse<IdDto> response = new CommonResponse<>(
                new CommonResponse.Header(true, 201, "success")
                ,
                List.of(new IdDto(expectedId)),
                1
        );

        given(restTemplate.exchange(
                        anyString(),
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
    }

    @Test
    void getAccountInfo() {
    }

    @Test
    void getAccountList() {
    }

    @Test
    void doAccountUpdate() {
    }

    @Test
    void getAccountRedis() {
    }

    @Test
    void getLoginInfo() {
    }
}