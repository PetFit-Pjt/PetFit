package com.port.petfit.user.member.account;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetController {

	@Autowired
	private PetService petService;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
    private UserRepository userRepository; // User 정보를 가져오는 데 사용
	
	public boolean isChipNumExists(String chipNum) {
        Pet pet = petRepository.findByChipNum(chipNum);
        return pet != null;
    }
	
	@GetMapping("/myPetRegister")
	public String showPetPage(Model model) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String userId = auth.getName(); // 현재 로그인한 사용자의 ID를 가져옴
	    User user = userRepository.findByUserId(userId); // 사용자 정보를 데이터베이스에서 가져옴
	    
	    // 새로운 반려동물 객체 생성
	    Pet pet = new Pet();
	    pet.setUser(user); // 현재 로그인한 사용자와 연결
	    
	    // 모델에 반려동물 객체 추가
	    model.addAttribute("Pet", pet);
	    
	    // 등록 폼을 보여주는 뷰로 이동
	    return "myPetRegister";
	}

	@PostMapping("/myPetRegister")
	public String addPet(@ModelAttribute("Pet") Pet pet, Model model) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String userId = auth.getName(); // 현재 로그인한 사용자의 ID를 가져옴
	    User user = userRepository.findByUserId(userId); // 사용자 정보를 데이터베이스에서 가져옴
	    
	    // Pet 객체에 사용자 정보 설정
	    pet.setUser(user);
	    
	    // 중복 체크
	    if (petRepository.existsByChipNum(pet.getChipNum())) {
	        model.addAttribute("errorMessage", "이미 존재하는 칩 번호입니다.");
	        return "myPetRegister";
	    }
	    
	    // Pet 정보를 저장
	    petService.createPet(pet);
	    
	    // 등록 페이지로 리다이렉트
	    return "redirect:/myPetRegister";
	}

	@GetMapping("/pets")
    public String getAllPets(Model model) {
        List<Pet> pets = petService.getAllPets();
        model.addAttribute("pets", pets);
        return "petList"; // 모든 펫을 보여주는 뷰
    }

}