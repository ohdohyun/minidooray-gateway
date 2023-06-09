package com.nhn.sadari.minidooray.gateway.auth;

import com.nhn.sadari.minidooray.gateway.domain.account.AccountRedis;
import com.nhn.sadari.minidooray.gateway.domain.account.AccountInfo;
import com.nhn.sadari.minidooray.gateway.domain.account.AccountRegister;
import com.nhn.sadari.minidooray.gateway.exception.GitEmailNotFountException;
import com.nhn.sadari.minidooray.gateway.service.AccountService;
import com.nhn.sadari.minidooray.gateway.service.GitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class GitLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final GitService gitService;
    private final AccountService accountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("username", authentication.getName());

        String accessToken = getAccessToken().getTokenValue();

        // 쿠키 to 브라우저
        response.addCookie(new Cookie("sessionId", session.getId()));

        String email = gitService.getEmail(accessToken);
        if (Objects.isNull(email)) {
            //Exception
            throw new GitEmailNotFountException("이메일이 없습니다");
        }

        OAuth2User userDetails = (OAuth2User) authentication.getPrincipal();

        AccountRedis accountRedis = accountService.getAccountRedis(email);

        Long createdId = null;
        if (null == accountRedis.getAccountId()) {

            AccountRegister accountRegister = new AccountRegister(
                    //아이디 패스워드 이메일 이름
                    email, //asdfasef@git.com
                    "team_sadari_never_gives_up",
                    userDetails.getAttribute("email"), //ohdo@kakao.com
//                    "ohdo@kakao.com",
                    userDetails.getAttribute("name")

            );
            createdId = accountService.registerAccount(accountRegister).getId();

            AccountInfo returnedAccount = accountService.getAccountInfo(createdId);
            accountRedis.setAccountId(createdId);
            accountRedis.setLoginId(returnedAccount.getLoginId());
            accountRedis.setUsername(returnedAccount.getName());
            accountRedis.setStatus(returnedAccount.getStatus());

        }

        redisTemplate.opsForValue().set(session.getId(), accountRedis);
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
