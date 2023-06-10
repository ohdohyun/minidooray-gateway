package com.nhn.sadari.minidooray.gateway.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("username", authentication.getName());
//        session.setAttribute("accountId", authentication.getAuthorities());


        // 쿠키 to 브라우저
        response.addCookie(new Cookie("SESSION", session.getId()));

        //레디스 처리
        redisTemplate.opsForValue().set(session.getId(), authentication.getName());

        response.sendRedirect("/");
    }
}
