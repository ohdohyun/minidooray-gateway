package com.nhn.sadari.minidooray.gateway.auth;

import com.nhn.sadari.minidooray.gateway.domain.account.LoginRequest;
import com.nhn.sadari.minidooray.gateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AccountService accountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("username", authentication.getName());
//        session.setAttribute("accountId", authentication.getAuthorities());

        LoginRequest loginRequest = accountService.getAccountInfo(authentication.getName());


        // #TODO 여기서 데이터 받아서 레디스애 담고 ~~~ 하기

        // 쿠키 to 브라우저
        response.addCookie(new Cookie("sessionId", session.getId()));

        //레디스 처리
        redisTemplate.opsForValue().set(session.getId(), loginRequest);
//        redisTemplate.opsForValue().set(session.getId(), authentication.getName());

        response.sendRedirect("/");
    }
}
