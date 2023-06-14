package com.nhn.sadari.minidooray.gateway.auth;

import com.nhn.sadari.minidooray.gateway.domain.account.LoginRequest;
import com.nhn.sadari.minidooray.gateway.exception.GitEmailNotFountException;
import com.nhn.sadari.minidooray.gateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LoginRequest info = accountService.getLoginInfo(username);

        if (info == null) {
            throw new GitEmailNotFountException("UsernameNotFoundException");
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("user");
        return new User(info.getLoginId(), info.getPassword(),
                Collections.singletonList(authority));
    }


}
