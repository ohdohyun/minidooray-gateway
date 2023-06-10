package com.nhn.sadari.minidooray.gateway.Utils;

import javax.servlet.http.Cookie;
import java.util.Arrays;

public class CookieUtils {

    public static Cookie getSessionCookie(String cookieName, Cookie[] cookies) {
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookieName.equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);
        }

        return null;
    }
}


