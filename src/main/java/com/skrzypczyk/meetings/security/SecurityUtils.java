package com.skrzypczyk.meetings.security;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityUtils {
    public static String getLoggedUserNameFromRequest(HttpServletRequest request) {
        return request.getUserPrincipal().getName();
    }
}
