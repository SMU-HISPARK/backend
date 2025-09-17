package com.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
	        .csrf().disable() // 개발 단계에서는 CSRF 비활성화
	        .authorizeHttpRequests(auth -> auth
	            .anyRequest().permitAll() // 모든 요청 허용
	        );
//        	.authorizeHttpRequests(auth -> auth
//        	    .requestMatchers("/", "/shop/**", "/css/**", "/js/**").permitAll()
//        	    .anyRequest().authenticated()
//        	)
//            .oauth2Login(oauth2 -> oauth2
//            	    .defaultSuccessUrl("/schedule.html", true)
//            	     // 로그인 성공 시 이동할 URL
//            	    
//            	    
//            	    
//            	    );
        return http.build();
    }
}
