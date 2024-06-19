package com.port.petfit.user.member.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String userId, @RequestParam String userPw, HttpServletRequest request, HttpServletResponse response) {
        // 사용자 인증 로직 수행
        User user = userService.authenticate(userId, userPw);

        if (user != null) {
            // 세션에 사용자 정보를 저장합니다.	
            request.getSession().setAttribute("user", user);
            // 세션에 로그인 여부를 나타내는 속성 설정
            request.getSession().setAttribute("loggedIn", true); // 로그인 성공 시 true로 설정

            // 세션의 만료 시간 설정 (예: 20분)
            request.getSession().setMaxInactiveInterval(300);

            // 쿠키 설정
            Cookie sessionCookie = new Cookie("JSESSIONID", request.getSession().getId());
            sessionCookie.setHttpOnly(true);
            sessionCookie.setSecure(request.isSecure());
            sessionCookie.setMaxAge(300);
            
            // 도메인과 경로를 명시
            sessionCookie.setDomain(request.getServerName());
            sessionCookie.setPath("/");
            
            response.addCookie(sessionCookie);

            // 로그인 성공 시 리디렉션
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/user")
                    .build();
        } else {
            // 인증 실패 시 에러 메시지 반환
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/login-failure")
                    .build();
        }
    }
}
