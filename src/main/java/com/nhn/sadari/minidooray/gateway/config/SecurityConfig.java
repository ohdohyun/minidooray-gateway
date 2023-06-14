package com.nhn.sadari.minidooray.gateway.config;

import com.nhn.sadari.minidooray.gateway.auth.CustomLoginSuccessHandler;
import com.nhn.sadari.minidooray.gateway.auth.CustomUserDetailsService;
import com.nhn.sadari.minidooray.gateway.auth.GitLoginSuccessHandler;
import com.nhn.sadari.minidooray.gateway.service.AccountService;
import com.nhn.sadari.minidooray.gateway.service.GitService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers("/login", "/members/**", "/index").permitAll()
                .antMatchers("/test").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/normal-login")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .successHandler(customLoginSuccessHandler(null, null))
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/")
                .loginPage("/login")
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService())
                .successHandler(gitLoginSuccessHandler(null, null, null, null))
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .and()
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler gitLoginSuccessHandler(RedisTemplate<String,Object> redisTemplate, OAuth2AuthorizedClientService authorizedClientService, GitService gitService, AccountService accountService) {
        return new GitLoginSuccessHandler(redisTemplate, authorizedClientService, gitService, accountService);
    }

    @Bean
    public AuthenticationSuccessHandler customLoginSuccessHandler(RedisTemplate<String,Object> redisTemplate, AccountService accountService) {
        return new CustomLoginSuccessHandler(redisTemplate, accountService);
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(github());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    private ClientRegistration github() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .userNameAttributeName("name")
                .clientId("da88f02bee356fe1a736")
                .clientSecret("9973b9769ef2a463e0fc4fd83af4ca00e23d67e7")
                .scope("user:email")
                .redirectUri("{baseUrl}/login/oauth2/code/github")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();
    }


}
