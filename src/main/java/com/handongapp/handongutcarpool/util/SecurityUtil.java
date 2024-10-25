package com.handongapp.handongutcarpool.util;

import com.handongapp.handongutcarpool.exception.NoAuthenticationException;
import com.handongapp.handongutcarpool.security.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    /**
     * 현재 인증된 사용자의 PrincipalDetails 반환
     */
    public static PrincipalDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NoAuthenticationException("No authenticated user found");
        }

        return (PrincipalDetails) authentication.getPrincipal();
    }

    /**
     * 현재 로그인된 유저의 ID 반환
     */
    public static String getCurrentUserId() {
        return getCurrentUser().getTbuser().getId();
    }

    /**
     * 현재 로그인된 유저의 학번 반환
     */
    public static String getCurrentUserHakbun() {
        return getCurrentUser().getTbuser().getHakbun();
    }
}