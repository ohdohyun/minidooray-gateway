package com.nhn.sadari.minidooray.gateway.auth;

import com.nhn.sadari.minidooray.gateway.domain.account.AccountModify;
import com.nhn.sadari.minidooray.gateway.domain.account.LoginRequest;
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

        // #TODO
        LoginRequest account = accountService.getAccountInfo(username);

        if (account == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("user");
        return new User(account.getLoginId(), account.getPassword(),
                Collections.singletonList(authority));
    }


}
