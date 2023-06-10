package com.nhn.sadari.minidooray.gateway.config;

import com.nhn.sadari.minidooray.gateway.auth.CustomLoginSuccessHandler;
import com.nhn.sadari.minidooray.gateway.auth.CustomUserDetailsService;
import com.nhn.sadari.minidooray.gateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                .antMatchers("/posts/**").authenticated()
                .and()
                .formLogin()
//                    .loginPage("/login")
//                    .loginProcessingUrl("/normal-login")
                //.usernameParameter("loginId")
                //.passwordParameter("password")
                .successHandler(customLoginSuccessHandler(null, null))
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
    public AuthenticationSuccessHandler customLoginSuccessHandler(RedisTemplate redisTemplate, AccountService accountService) {
        return new CustomLoginSuccessHandler(redisTemplate, accountService);
    }


}
