package com.handongapp.handongutcarpool.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handongapp.handongutcarpool.repository.TbuserRepository;
import com.handongapp.handongutcarpool.security.CustomAuthenticationProvider;
import com.handongapp.handongutcarpool.security.FilterExceptionHandlerFilter;
import com.handongapp.handongutcarpool.security.JwtAuthenticationFilter;
import com.handongapp.handongutcarpool.security.JwtAuthorizationFilter;
import com.handongapp.handongutcarpool.service.AuthService;
import com.handongapp.handongutcarpool.util.ExternalProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
	
	private final TbuserRepository tbuserRepository;
	private final CorsFilterConfiguration corsFilterConfiguration;
	private final ObjectMapper objectMapper;
	private final AuthService authService;
	private final ExternalProperties externalProperties;
	private final CustomAuthenticationProvider customAuthenticationProvider;

	public SecurityConfig(TbuserRepository tbuserRepository, CorsFilterConfiguration corsFilterConfiguration, ObjectMapper objectMapper, AuthService authService
			, ExternalProperties externalProperties, CustomAuthenticationProvider customAuthenticationProvider) {
		this.tbuserRepository = tbuserRepository;
		this.corsFilterConfiguration = corsFilterConfiguration;
		this.objectMapper = objectMapper;
		this.authService = authService;
		this.externalProperties = externalProperties;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// CustomAuthenticationProvider 사용을 위해 authenticationManager 를 빈으로 등록
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder =
				http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
		return authenticationManagerBuilder.build();
	}
	
    /**
	 *  Spring Security 권한 설정.
	 */
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf(AbstractHttpConfigurer::disable)
//					.authorizeHttpRequests(auth -> auth
//							.requestMatchers("/api/login", "/api/register", "/api/refresh").permitAll()
//							.anyRequest().authenticated()
//					)
//				//세션 사용안함 (STATELESS)
//				.authenticationProvider(customAuthenticationProvider)
//				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.formLogin(AbstractHttpConfigurer::disable)
//				.httpBasic(AbstractHttpConfigurer::disable)
//				.addFilter(corsFilterConfiguration.corsFilter())
//				.apply(new CustomDsl());
//		return http.build();
//	}
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/login", "/api/tbuser/create", "/api/tbuser/refresh").permitAll()
						.anyRequest().authenticated()
				)
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션 비활성화
				)
				.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.addFilter(corsFilterConfiguration.corsFilter())
				.apply(new CustomDsl(authenticationManager));  // AuthenticationManager를 주입

		return http.build();
	}
					
	public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {

		private final AuthenticationManager authenticationManager;

		public CustomDsl(AuthenticationManager authenticationManager) {
			this.authenticationManager = authenticationManager;
		}
	    /**
		 *  Jwt Token Authentication을 위한 filter 설정.
		 *  
		 *  jwtAuthenticationFilter: 인증을 위한 필터("/api/login")
		 *  JwtAuthorizationFilter: 인가를 위한 필터
		 *  FilterExceptionHandlerFilter: TokenExpiredException 핸들링을 위한 필터 
		 */
		@Override
		public void configure(HttpSecurity http) throws Exception {
			JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, objectMapper, authService, externalProperties);
			jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

			http.addFilter(corsFilterConfiguration.corsFilter())
					.addFilter(jwtAuthenticationFilter)
					.addFilter(new JwtAuthorizationFilter(authenticationManager, tbuserRepository, authService, externalProperties))
					.addFilterBefore(new FilterExceptionHandlerFilter(), BasicAuthenticationFilter.class);
		}
		
	}

}