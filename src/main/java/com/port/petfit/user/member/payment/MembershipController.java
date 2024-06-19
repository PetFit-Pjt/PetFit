package com.port.petfit.user.member.payment;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MembershipController {

	private final MembershipService membershipService;

	public MembershipController(MembershipService membershipService) {
		this.membershipService = membershipService;
	}

	@GetMapping("/Membership")
	public String ShowMemberShipPage() {
		return "Membership";
	}
	
    @GetMapping("/userMembership")
    public String getMembership(Model model) {
        List<Membership> memberships = membershipService.getAllMemberships(); // 멤버십 정보를 가져오는 서비스 메서드 호출
        model.addAttribute("memberships", memberships); // 모델에 멤버십 정보 리스트를 추가
        return "userMembership"; // Thymeleaf 템플릿 페이지 이름 반환
    }

	@GetMapping("/Membership_checkout")
	public String ShowMemberShipCheckout() {
		return "Membership_checkout";
	}

	@GetMapping("/pay")
	public String payview() {
		return "pay";
	}

}
