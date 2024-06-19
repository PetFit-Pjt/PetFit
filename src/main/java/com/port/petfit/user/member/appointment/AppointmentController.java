package com.port.petfit.user.member.appointment;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.port.petfit.user.member.account.User;
import com.port.petfit.user.member.account.UserRepository;
import com.port.petfit.user.member.payment.Membership;
import com.port.petfit.user.member.payment.MembershipRepository;
import com.port.petfit.user.member.payment.PaymentController;

@Controller
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private MembershipRepository membershipRepository;
	private static final Logger LOGGER = Logger.getLogger(PaymentController.class.getName());
	
	@GetMapping("/appointment")
	public String createAppointment(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName(); // 현재 로그인한 사용자의 ID를 가져옴
		User user = userRepository.findByUserId(userId); // 사용자 정보를 데이터베이스에서 가져옴

		// 사용자의 멤버십 상태 확인
		Membership membership = membershipRepository.findByUser(user);
		boolean isMemberActive = membership != null && "Active".equals(membership.getMembershipstatus());
		LOGGER.info("User Membership Active: " + isMemberActive); // 로그 추가
		model.addAttribute("isMemberActive", isMemberActive);

		// 예약 정보를 생성하고 사용자 정보를 설정
		Appointment appointment = new Appointment();
		appointment.setUser(user);
		model.addAttribute("Appointment", appointment);
		model.addAttribute("services", new String[]{"서비스 선택", "진료", "예방 접종", "미용", "수술"});

		return "appointment";
	}
    
	@PostMapping("/appointment")
	public String createAppointment(@ModelAttribute("Appointment") Appointment appointment) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String userId = auth.getName(); // 현재 로그인한 사용자의 ID를 가져옴
	    User user = userRepository.findByUserId(userId); // 사용자 정보를 데이터베이스에서 가져옴
	    
	    // Pet 객체에 사용자 정보 설정
	    appointment.setUser(user);
	    
	    // Pet 정보를 저장
	    appointmentService.createAppointment(appointment);
	    
	    // 등록 페이지로 리다이렉트
	    return "redirect:/appointment_success";
	}

    
	@GetMapping("/appointment_success")
	public String showAppointmentView(Model model) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String userId = auth.getName(); // 현재 로그인한 사용자의 ID를 가져옴
	    
	    List<Appointment> appointments = appointmentRepository.findByUser_userId(userId);
	    model.addAttribute("appointment_lists", appointments);
	    
	    return "appointment_success";
	}
	
	@GetMapping("/appointment_list")
	public String listAppointments(Model model) {
		List<Appointment> appointments = appointmentRepository.findAll();
		model.addAttribute("appointment_lists", appointments);
		return "appointment_list"; // Thymeleaf 템플릿 이름
	}

	@PostMapping("/appointment_list/{appointmentId}")
	public String approveAppointment(@PathVariable Integer appointmentId, RedirectAttributes redirectAttributes) {
		try {
			
			Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
			appointment.setApproved(true);
			appointmentRepository.save(appointment);
			redirectAttributes.addFlashAttribute("successMessage", "예약 #" + appointmentId + "이(가) 승인되었습니다.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "예약 승인 중 오류가 발생했습니다: " + e.getMessage());
		}
		return "redirect:/appointment_list"; // 승인 후 예약 목록 페이지로 리다이렉트
	}
    
	@DeleteMapping("/appointment_list/delete/{appointmentId}")
	public String deleteAppointment(@PathVariable Integer appointmentId, RedirectAttributes redirectAttributes) {
	    try {
	        appointmentRepository.deleteById(appointmentId);
	        redirectAttributes.addFlashAttribute("successMessage", "예약 #" + appointmentId + "이(가) 삭제되었습니다.");
	    } catch (EmptyResultDataAccessException e) {
	        // 해당 ID의 예약이 없는 경우
	        redirectAttributes.addFlashAttribute("errorMessage", "예약 #" + appointmentId + "을(를) 찾을 수 없습니다.");
	    } catch (Exception e) {
	        // 그 외 예외 처리
	        redirectAttributes.addFlashAttribute("errorMessage", "예약 삭제 중 오류가 발생했습니다: " + e.getMessage());
	    }
	    return "redirect:/appointment_list"; // 삭제 후 예약 목록 페이지로 리다이렉트
	}
}