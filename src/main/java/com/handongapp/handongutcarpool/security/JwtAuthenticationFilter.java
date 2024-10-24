package com.handongapp.handongutcarpool.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handongapp.handongutcarpool.service.AuthService;
import com.handongapp.handongutcarpool.dto.TbuserDto;
import com.handongapp.handongutcarpool.util.ExternalProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Objects;

@Transactional
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final ObjectMapper objectMapper;
	private final AuthService authService;
	private final ExternalProperties externalProperties;

	/**
     *  로그인하려는 사용자의 자격을 확인해 토큰을 발급하는 함수.
	 *  "/api/login" 으로 들어오는 요청에 실행됨.
	 *  생성된 Authentication이 SecurityContextHolder에 등록되어 권한처리가 가능하게 함.
	 *  
	 *  @throws AuthenticationException
	 */
	@Transactional
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		Authentication authentication = null;
		TbuserDto.LoginReqDto tbuserLoginDto = null;
		try {
			tbuserLoginDto = objectMapper.readValue(request.getInputStream(), TbuserDto.LoginReqDto.class);
		} catch (IOException e) {
			logger.info("🚨 login attemptAuthentication(1) : Not Enough Parameters.");
			//e.printStackTrace();
		}

		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(Objects.requireNonNull(tbuserLoginDto).getHakbun(), tbuserLoginDto.getPhoneNumber());
			authentication = authenticationManager.authenticate(authenticationToken);
		} catch (AuthenticationException e) {
			logger.info("🚨 login attemptAuthentication(2): Data Not Matched.");
			//e.printStackTrace();
		}
		
		return authentication;
	} 
	
	/**
     *  로그인 완료시 호출되는 함수.
     *  Refresh Token을 발급해 HttpServletRespons에 담는다.
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
		// TbuserId로 리프레시토큰 발급
		logger.info("📍 principalDetails.getTbuser().getId() : " + principalDetails.getTbuser().getId());
		String refreshToken = authService.createRefreshToken(principalDetails.getTbuser().getId());

		//String accessToken = authService.createAccessToken(principalDetails.getTbuser().getId());
//		JwtTokenDto jwtTokenDto = authService.issueAccessToken(refreshToken);

		// header에 담아서 전달
		response.addHeader(externalProperties.getRefreshKey(), externalProperties.getTokenPrefix() + refreshToken);
//		response.addHeader(externalProperties.getAccessKey(), externalProperties.getTokenPrefix() + jwtTokenDto.getAccessToken());
		// 바디에도 담아줍시다.
		//TbuserDto.TbuserLoginDto tbuserLoginDto = new TbuserDto.TbuserLoginDto(externalProperties.getTokenPrefix() + refreshToken, externalProperties.getTokenPrefix() + accessToken);
		//response.getWriter().write(tbuserLoginDto.toString());
		//response.getWriter().write(externalProperties.getTokenPrefix() + refreshToken);
		
		logger.info("📍 successfulAuthentication : login success");
	}
	
}