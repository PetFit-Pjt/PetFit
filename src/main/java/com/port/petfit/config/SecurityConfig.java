package com.port.petfit.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
	
	 @Autowired
	    private CustomUserDetailsService userDetailsService;
	 
	 @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService);
	    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .csrf().disable() 
        
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/appointment","/pay").hasAnyRole("USER","ADMIN") // 로그인 성공 페이지 및 /index는 인증된 사용자만 접근 가능             
                .requestMatchers("/index_admin").hasRole("ADMIN")
                .requestMatchers("/index_hospital").hasAnyRole("HOSPITAL","ADMIN")
                .requestMatchers("/**").permitAll() // 로그인 페이지와 정적 자원에 대한 접근 허용
                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
            )
            .formLogin(form -> form
                .loginPage("/login")  // 사용자 지정 로그인 페이지
                .loginProcessingUrl("/login")
                .failureUrl("/login?error=true")
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .successHandler(myAuthenticationSuccessHandler())
                .permitAll()
            )
            
          
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")  // 로그아웃 성공 시 리다이렉트
                .invalidateHttpSession(true)  // HTTP 세션 무효화
                .deleteCookies("JSESSIONID")  // JSESSIONID 쿠키 삭제
                .clearAuthentication(true)  // 인증 정보 삭제
                .addLogoutHandler((request, response, authentication) -> {
                    if (authentication != null && authentication.getDetails() != null) {
                        try {
                            request.getSession().setAttribute("logoutMessage", "로그아웃 되었습니다.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .permitAll()
                
            );

        return http.build();
        
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            authentication.getAuthorities().stream().forEach(authority -> {
            	  try {
                      switch (authority.getAuthority()) {
                          case "ROLE_ADMIN":
                              setCookie(response, "adminAccess", 7); // 7일간 유효한 쿠키 설정
                              response.sendRedirect("/index_admin");
                              break;
                          case "ROLE_HOSPITAL":
                              setCookie(response, "hospitalAccess", 7); // 7일간 유효한 쿠키 설정
                              response.sendRedirect("/index_hospital");
                              break;
                          default:
                              setCookie(response, "userAccess", 7); // 7일간 유효한 쿠키 설정
                              response.sendRedirect("/index");
                              break;
                      }
                  } catch (IOException e) {
                      // 로그 기록 또는 오류 처리
                      e.printStackTrace(); // 실제 운영 환경에서는 적절한 로깅 프레임워크를 사용하여 로그를 기록하는 것이 좋습니다.
                  }
            });
        };
    }


private void setCookie(HttpServletResponse response, String name, int daysToExpire) {
    Cookie cookie = new Cookie(name, "true");
    cookie.setMaxAge(daysToExpire * 24 * 60 * 60);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
}

private LogoutHandler customLogoutHandler() {
    return (request, response, authentication) -> {
        if (authentication != null) {
            authentication.getAuthorities().forEach(authority -> {
                String cookieName = "";
                switch (authority.getAuthority()) {
                    case "ROLE_ADMIN":
                        cookieName = "adminAccess";
                        break;
                    case "ROLE_HOSPITAL":
                        cookieName = "hospitalAccess";
                        break;
                    default:
                        cookieName = "userAccess";
                        break;
                }
                Cookie cookie = new Cookie(cookieName, null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            });
            try {
                request.getSession().setAttribute("logoutMessage", "로그아웃 되었습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
}