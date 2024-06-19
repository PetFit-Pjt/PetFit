package com.port.petfit.user.member.account;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private HospitalRepository hospitalRepository;

//	@PostMapping("/modifyhospital")
//	public String modifyHospitalAndThumbnail(@RequestParam("hospitalId") String hospitalId,
//	        @RequestParam("file") MultipartFile file, @ModelAttribute("Hospital") Hospital hospital,
//	        @RequestParam("confirm-password") String confirmPassword, BindingResult result, Model model) {
//	    if (result.hasErrors() || !hospital.getHospitalPw().equals(confirmPassword)) {
//	        model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
//	        return "modifyregister"; // 에러가 있을 경우 다시 수정 페이지로
//	    }
//
//	    try {
//	        // 병원 정보 가져오기
//	        Hospital existingHospital = hospitalRepository.findByHospitalId(hospitalId);
//	        if (existingHospital == null) {
//	            return "병원을 찾을 수 없습니다.";
//	        }
//
//	        // 이미지 파일 저장 및 업로드
//	        hospitalService.saveHospitalThumbnail(existingHospital, file);
//
//	        // 병원 정보 업데이트
//	        hospitalService.updateHospital(hospital);
//
//	        return "redirect:/index_hospital"; // 업데이트 후 병원 홈으로 리다이렉트
//	    } catch (IOException e) {
//	        return "이미지 업로드 중 오류가 발생했습니다.";
//	    }
//	}


    @GetMapping("/deleteHospital")
    public ResponseEntity<String> deleteHospital(@RequestParam("hospitalId") String hospitalId) {
        try {
            hospitalService.deleteHospital(hospitalId);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error: " + e.getMessage());
        }
    }
    
}
