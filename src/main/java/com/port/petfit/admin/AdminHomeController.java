package com.port.petfit.admin;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {

    @GetMapping("/admin")
    public String home(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            // 사용자가 인증되었을 경우, 로그인 상태를 모델에 추가
            model.addAttribute("loggedIn", true);
        } else {
            // 사용자가 인증되지 않았을 경우, 로그인 상태를 모델에 추가하지 않음
            model.addAttribute("loggedIn", false);
        }
		return "index_admin";	
	}
}