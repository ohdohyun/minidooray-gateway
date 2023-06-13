package com.nhn.sadari.minidooray.gateway.auth;

import com.nhn.sadari.minidooray.gateway.domain.account.AccountRedis;
import com.nhn.sadari.minidooray.gateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    private final AccountService accountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("username", authentication.getName());

        // AccountRedis 바꾸기
        AccountRedis accountRedis = accountService.getAccountRedis(authentication.getName());
        // 계정이 없으면 다시 로그인 페이지로 보내기
        if (Objects.isNull(accountRedis)) {
            response.sendRedirect("/login");
        }

        // 쿠키 브라우저에 저장
        response.addCookie(new Cookie("sessionId", session.getId()));

        //레디스 처리 1시간 후 삭제
        redisTemplate.opsForValue().set(session.getId(), accountRedis);
        redisTemplate.expire(session.getId(), 1, TimeUnit.HOURS);

        response.sendRedirect("/");
    }
}
