package com.nhn.sadari.minidooray.gateway.controller;

import com.nhn.sadari.minidooray.gateway.domain.account.AccountInfo;
import com.nhn.sadari.minidooray.gateway.domain.account.AccountRedis;
import com.nhn.sadari.minidooray.gateway.domain.account.AccountRegister;
import com.nhn.sadari.minidooray.gateway.enumclass.MemberStatusType;
import com.nhn.sadari.minidooray.gateway.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private RedisTemplate redisTemplate;
    @MockBean
    private RestTemplate restTemplate;
    @BeforeEach
    void initTest() throws Exception {
        when(redisTemplate.opsForValue().get(any()))
                .thenReturn(
                        new AccountRedis(
                                1L,
                                "test loginId;",
                                "test username;",
                                MemberStatusType.가입
                        )
                );
    }

    @Test
    void accountRegisterTest() throws Exception {

        mockMvc.perform(get("/accounts/register"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/account_register"));
    }

    @Test
    void doRegisterTest() throws Exception {
        AccountRegister accountRegister = new AccountRegister("testId", "1234", "test@email.com", "tester");

        mockMvc.perform(post("/accounts/register"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:index"));

        verify(accountService).registerAccount(any(AccountRegister.class));
    }

    @Test
    void deleteAccountTest() throws Exception {
        mockMvc.perform(get("/accounts/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:index"));

        verify(accountService).deleteAccount(anyLong());
    }

    @Test
    void accountModifyTest() throws Exception {
        AccountInfo accountInfo = new AccountInfo("testId", "1234", "test@email.com", "tester", MemberStatusType.가입);
        when(accountService.getAccountInfo(anyLong())).thenReturn(accountInfo);

        ResultActions result = mockMvc.perform(get("/accounts/modify/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("accountId", "accountUpdate"))
                .andExpect(view().name("account/account_modify"));

        verify(accountService).getAccountInfo(anyLong());
    }

    @Test
    void doModifyTest() throws Exception {
        AccountInfo accountInfo = new AccountInfo("testId", "1234", "test@email.com", "tester", MemberStatusType.가입);

        mockMvc.perform(post("/accounts/modify/1")
                        .flashAttr("accountUpdate", accountInfo))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:index"));

        verify(accountService).doAccountUpdate(any(AccountInfo.class), anyLong());
    }
}
