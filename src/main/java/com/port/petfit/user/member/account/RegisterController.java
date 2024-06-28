package com.port.petfit.user.member.account;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.port.petfit.user.member.board.Board;
import com.port.petfit.user.member.board.BoardRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterController {

	@Autowired
	private UserService userService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private BoardRepository boardRepository;
    
	
    @GetMapping("/index")
    public String home(Model model, Authentication authentication, HttpServletRequest request) {
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated();
        HttpSession session = request.getSession(); // HttpServletRequest에서 세션을 가져옴
        session.setAttribute("isLoggedIn", isLoggedIn); // 세션에 로그인 상태 저장
        model.addAttribute("isLoggedIn", isLoggedIn);
        
        // 최신 게시글 4개 조회
        List<Board> latestPost = boardRepository.findTop5ByOrderByCreatedDateDesc();
        
        // 메인 페이지에 최신 게시글 전달
        model.addAttribute("latestPost", latestPost);
        
        return "index";
    }
    
    @GetMapping("/index_hospital")
    public String hospitalsession(Model model, Authentication authentication, HttpServletRequest request) {
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated();
        HttpSession session = request.getSession(); // HttpServletRequest에서 세션을 가져옴
        session.setAttribute("isLoggedIn", isLoggedIn); // 세션에 로그인 상태 저장
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "index_hospital";
    }
	
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		// user 객체를 초기화
		User user = new User();

		// user 객체를 모델에 추가
		model.addAttribute("User", user);

		// "register" 템플릿을 반환하여 뷰를 렌더링
		return "register";
	}

	@GetMapping("/register-admin")
	public String showRegisterAdminPage(Model model) {
		// admin 객체를 초기화
		Admin admin = new Admin();

		// admin 객체를 모델에 추가
		model.addAttribute("Admin", admin);

		// "register-admin" 템플릿을 반환하여 뷰를 렌더링
		return "register-admin";
	}

	@GetMapping("/register-hospital")
	public String showRegisterHospitalPage(Model model) {
		// hospital 객체를 초기화
		Hospital hospital = new Hospital();

		// hospital 객체를 모델에 추가
		model.addAttribute("Hospital", hospital);

		// "register-hospital" 템플릿을 반환하여 뷰를 렌더링
		return "register-hospital";
	}

	@PostMapping("/register-admin")
	public String registerAdmin(@ModelAttribute Admin admin) {
		adminService.registerAdmin(admin);
		return "login";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user) {
		// 비밀번호를 암호화
		userService.registerUser(user);

		return "login"; // 로그인 페이지로 리다이렉트
	}
	
	@PostMapping("/register-hospital")
	public String registerHospital(@ModelAttribute Hospital hospital) {
		hospitalService.registerHospital(hospital);

		return "login"; // 로그인 페이지로 리다이렉트
	}
	
	@GetMapping("/success")
	public String success() {
		return "success";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/login-success")
	public String loginSuccessPage() {
		return "login-success";
	}

	@GetMapping("/login-failure")
	public String loginfailurePage() {
		return "login-failure";
	}

	@GetMapping("/index_admin")
	public String adminHomePage() {
		return "index_admin";
	}

	@GetMapping("/reservation_list")
	public String register_listPage() {
		return "reservation_list";
	}

	@GetMapping("/userMyPage")
	public String showUserMyPage() {
		return "userMyPage";
	}
	
	@GetMapping("/userReservation")
	public String showUserReservationPage() {
		return "userReservation";
	}
	
	
	@GetMapping("/mypetInfo")
	public String showMypetInfoPage() {
		return "mypetInfo";
	}
	
	@GetMapping("/reservation")
	public String showReservationPage() {
		return "reservation";
	}
	
	@GetMapping("/member_management")
	public String showMemberManagementPage(Model model) {
	    List<User> users = userRepository.findAll(); // 모든 유저 정보 가져오기
	    model.addAttribute("User", users); // 모델에 유저 정보 추가
	    return "member_management"; // 회원 관리 페이지로 이동
	}
	@GetMapping("/modifyUser")
	public String membereditUser(@RequestParam("userId") String userId, Model model) {
	    User user = userRepository.findByUserId(userId);
	    model.addAttribute("User", user);
	    return "modifyUser"; // 수정 페이지 뷰 반환
	}

	@PostMapping("/modifyUser")
	public String memberupdateUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
	    String userId = user.getUserId(); // 업데이트할 사용자의 ID 가져오기
	    userService.updateUser2(userId, user); // updateUser2 메서드 호출 시 userId와 user 전달
	    redirectAttributes.addFlashAttribute("successMessage", "사용자 정보가 업데이트되었습니다.");
	    return "redirect:/member_management";
	}

	 // 계정 복구 폼을 보여주는 메소드
    @GetMapping("/recover-account")
    public String showRecoveryForm() {
        return "recover-account";  // 계정 복구 폼이 있는 HTML 파일
    }

    // 계정 복구 요청을 처리하는 메소드
    
    @PostMapping("/recover-account")
    public String handleRecoveryRequest(@RequestParam String email, Model model) {
        boolean recoveryResult = userService.recoverAccount(email);
        if (recoveryResult) {
            model.addAttribute("message", "아이디와 임시 비밀번호가 이메일로 발송되었습니다. 이메일을 확인해 주세요.");
        } else {
            model.addAttribute("error", "해당 이메일로 등록된 계정을 찾을 수 없습니다.");
        }
        return "recovery-response";  // 결과를 보여주는 뷰 페이지
    }
    
    @GetMapping("/lookingfor")
    public String lookinforPage() {
    	return "lookingfor";
    			
    }
    
	
	
    @GetMapping("/mypage_user")
    public String myuser_page(Model model, Principal principal) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName(); // 현재 사용자 이름 가져오기
        User user = userRepository.findByUserId(userId); // 데이터베이스에서 사용자 정보 조회
        model.addAttribute("User", user); // 모델에 사용자 정보 추가
        return "mypage_user"; // 사용자 정보를 담은 모델을 뷰로 전달
    }
    
    @GetMapping("/modifyregister")
    public String editUser(@RequestParam("userId") String userId, Model model) {
        User user = userRepository.findByUserId(userId);
        model.addAttribute("User", user);
        return "modifyregister"; // 수정 페이지 뷰 반환
    }
    
    @PostMapping("/modifyregister")
    public String updateUser(@ModelAttribute("User") User user, @RequestParam("confirm-password") String confirmPassword, BindingResult result, Model model) {
        if (result.hasErrors() || !user.getUserPw().equals(confirmPassword)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "modifyregister"; // 에러가 있을 경우 다시 수정 페이지로
        }
        userService.updateUser(user); // 사용자 정보 업데이트
        return "redirect:/mypage_user"; // 업데이트 후 마이 페이지로 리다이렉트
    }
    
    @GetMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam("userId") String userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error: " + e.getMessage());
        }
    }

	
	@GetMapping("/hospital_management")
	public String hospital_managementPage() {
		return "hospital_management";
	}

	@Autowired
	private HospitalRepository hospitalRepository;

	@GetMapping("/hospital_list")
	public String listHospitals(Model model) {
		List<Hospital> hospitals = hospitalRepository.findAll();
		model.addAttribute("hospitals", hospitals);
		return "hospital_list"; // Thymeleaf 템플릿 이름
	}

	@GetMapping("/hospitalMyPage")
	public String showhospitalMyPage(Model model, Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String hospitalId = auth.getName();
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
		model.addAttribute("hospital", hospital);
		return "hospitalMyPage";
	}
	
	
	@GetMapping("/modifyhospital/{hospitalId}")
	public String showModifyHospitalPage(@PathVariable String hospitalId, Model model) {
	    Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
	    model.addAttribute("Hospital", hospital);
	    return "modifyhospital"; // 수정 페이지 뷰 반환
	}
	
	@PostMapping("/modifyhospital/{hospitalId}")
	public String modifyHospitalAndThumbnail(@PathVariable String hospitalId,
	                                         @RequestParam("file") MultipartFile file,
	                                         @ModelAttribute("Hospital") Hospital updatedHospital,
	                                         @RequestParam("confirm-password") String confirmPassword,
	                                         @RequestParam(value = "new-password", required = false) String newPassword,
	                                         RedirectAttributes redirectAttributes) {
	    try {
	        // 현재 비밀번호 확인
	        Hospital hospital = hospitalService.authenticate(hospitalId, confirmPassword);
	        if (hospital == null) {
	            redirectAttributes.addFlashAttribute("errorMessage", "현재 비밀번호가 일치하지 않습니다.");
	            return "redirect:/modifyhospital/" + hospitalId;
	        }

	        // 이미지 파일 저장 및 업로드
	        hospitalService.saveHospitalThumbnail(hospital, file);

	        // 병원 정보 업데이트
	        hospitalService.updateHospital(hospitalId, updatedHospital, newPassword);

	        redirectAttributes.addFlashAttribute("successMessage", "병원 정보가 업데이트되었습니다.");
	        return "redirect:/index_hospital";
	    } catch (IOException e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "이미지 업로드 중 오류가 발생했습니다.");
	        return "redirect:/modifyhospital/" + hospitalId;
	    }
	}
    
	@PostMapping("/hospital_list/{hospitalId}")
	public String approveHospital(@PathVariable String hospitalId, RedirectAttributes redirectAttributes) {
		try {
			Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
			hospital.setApproved(true);
			hospitalRepository.save(hospital);
			redirectAttributes.addFlashAttribute("successMessage", "병원 #" + hospitalId + "이(가) 승인되었습니다.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "병원 승인 중 오류가 발생했습니다: " + e.getMessage());
		}
		return "redirect:/hospital_list"; // 승인 후 병원 목록 페이지로 리다이렉트
	}

	
	
}