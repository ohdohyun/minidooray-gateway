package com.nhn.sadari.minidooray.gateway.auth;

import com.nhn.sadari.minidooray.gateway.domain.account.AccountInfo;
import com.nhn.sadari.minidooray.gateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
public class GitLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    private final OAuth2AuthorizedClientService authorizedClientService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("username", authentication.getName());

//        AccountInfo accountInfo = accountService.getAccountInfo(authentication.getName());
        OAuth2AccessToken accessToken = getAccessToken();
        // 쿠키 to 브라우저
        response.addCookie(new Cookie("sessionId", session.getId()));

        //레디스 처리 1시간 후 삭제
//        redisTemplate.opsForValue().set(session.getId(), accountInfo);
        redisTemplate.expire(session.getId(), 1, TimeUnit.HOURS);

        response.sendRedirect("/");
    }

    public OAuth2AccessToken getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                "github", authentication.getName());

        if (authorizedClient != null) {
            return authorizedClient.getAccessToken();
        }

        return null;
    }
}
