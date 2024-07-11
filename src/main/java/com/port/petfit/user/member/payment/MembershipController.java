package com.port.petfit.user.member.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.port.petfit.user.member.account.User;
import com.port.petfit.user.member.account.UserRepository;

@Controller
public class MembershipController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private MembershipRepository membershipRepository;

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
    public String showPaymentPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User secUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            String username = secUser.getUsername(); // 로그인한 사용자의 아이디 가져오기

            User user = userRepository.findByUserId(username); // 사용자 정보 조회
            if (user != null) {
                model.addAttribute("userName", user.getUserName());
                model.addAttribute("userEmail", user.getUserEmail()); // 이메일 정보 추가

                Optional<Membership> membershipOpt = membershipRepository.findByUser(user);
                if (membershipOpt.isPresent()) {
                    Membership membership = membershipOpt.get();
                    boolean isActive = membership.isMembershipActive();
                    model.addAttribute("hasMembership", true);
                    model.addAttribute("isActive", isActive);
                    model.addAttribute("endDate", membership.getEndDate());
                } else {
                    model.addAttribute("hasMembership", false);
                }
            }
        }
        return "pay";
    }

}
