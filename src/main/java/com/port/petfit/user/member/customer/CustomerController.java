package com.port.petfit.user.member.customer;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

	// 전체 글 리스트 - 고객센터
    @GetMapping("/customer")
    public String showCustomerList(Model model) {
        return "customer"; // 뷰 이름을 반환. costumer.html
    }
    
    @GetMapping("/notice")
    public String showNoticeList(Model model) {
        return "notice"; // 뷰 이름을 반환
    }

    @GetMapping("/FAQ")
    public String showFAQList(Model model) {
        return "FAQ"; // 뷰 이름을 반환
    }
    
   

}

