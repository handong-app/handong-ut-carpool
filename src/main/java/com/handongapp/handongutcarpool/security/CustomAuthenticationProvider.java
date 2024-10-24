package com.handongapp.handongutcarpool.security;

import com.handongapp.handongutcarpool.domain.Tbuser;
import com.handongapp.handongutcarpool.repository.TbuserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final TbuserRepository tbuserRepository;

    public CustomAuthenticationProvider(TbuserRepository tbuserRepository) {
        this.tbuserRepository = tbuserRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String hakbun = authentication.getName();  // 입력된 학번
        String phoneNumber = authentication.getCredentials().toString();  // 입력된 전화번호

        // DB에서 유저 조회
        Tbuser user = tbuserRepository.findByHakbun(hakbun)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with hakbun: " + hakbun));

        // 전화번호 비교
        if (!user.getPhoneNumber().equals(phoneNumber)) {
            throw new BadCredentialsException("Invalid phone number");
        }

        // PrincipalDetails 생성 후 반환
        PrincipalDetails principalDetails = new PrincipalDetails(user);
        return new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}