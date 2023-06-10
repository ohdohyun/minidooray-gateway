package com.nhn.sadari.minidooray.gateway.interceptor;

import com.nhn.sadari.minidooray.gateway.Utils.CookieUtils;
import com.nhn.sadari.minidooray.gateway.domain.account.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie cookie = CookieUtils.getSessionCookie("sessionId", request.getCookies());
//        String loginId = String.valueOf(redisTemplate.opsForValue().get(cookie.getValue()));

        LoginRequest loginRequest = (LoginRequest)(redisTemplate.opsForValue().get(cookie.getValue()));
//        System.out.println("::::::loginId = " + loginId);
        request.setAttribute("loginRequest", loginRequest);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
