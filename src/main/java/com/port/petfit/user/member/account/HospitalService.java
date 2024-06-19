package com.port.petfit.user.member.account;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.port.petfit.config.PasswordUtil;

import jakarta.transaction.Transactional;

@Service
public class HospitalService {

	@Autowired
	private HospitalRepository hospitalRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void registerHospital(Hospital hospital) {
		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(hospital.getHospitalPw());

		// 암호화된 비밀번호를 Hospital 객체에 설정
		hospital.setHospitalPw(encodedPassword);

		// 데이터베이스에 사용자 정보 저장
		hospitalRepository.save(hospital);
	}
	
    public void saveHospital(Hospital hospital) {
        hospitalRepository.save(hospital);
    }
	
    public void saveHospitalThumbnail(Hospital hospital, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("이미지 파일이 비어 있습니다.");
        }

        try {
            // 이미지를 저장할 경로 설정
            String uploadDir = "C:/Hospitals/mit/Downloads/hospital_thumbnail";

            // 디렉토리가 없으면 생성
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 파일명 설정 (고유한 파일명을 위해 hospitalId와 UUID를 조합)
            String fileName = hospital.getHospitalId() + "_" + UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            // 파일 경로 설정
            String filePath = uploadDir + File.separator + fileName;
            File dest = new File(filePath);

            // 파일 저장
            file.transferTo(dest);

            // 저장된 파일의 경로를 병원 엔티티에 저장
            hospital.setHospitalThumbnail(filePath);
            // 엔티티 저장
            hospitalRepository.save(hospital);
        } catch (IOException e) {
            throw new RuntimeException("이미지 파일 저장 중 오류가 발생했습니다.", e);
        }
    }


	public Hospital authenticate(String hospitalId, String hospitalPw) {
		// 사용자 정보 검색
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);

		// 사용자가 존재하고 비밀번호가 일치하는지 확인
		if (hospital != null && PasswordUtil.matches(hospitalPw, hospital.getHospitalPw())) {
			return hospital; // 인증 성공 시 사용자 반환
		}

		// 인증 실패 시 null 반환
		return null;
	}
	
	public String getCurrentHospitalId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // 사용자명을 ID로 사용하는 경우
        }
        return null;
    }
	
    @Transactional
    public void updateHospital(Hospital updatedHospital) {
        String hospitalId = getCurrentHospitalId(); // 현재 사용자 ID 가져오기
        Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
        
        // 받은 객체에서 가져온 정보로 업데이트
        hospital.setHospitalPw(passwordEncoder.encode(updatedHospital.getHospitalPw())); // 비밀번호 암호화하여 업데이트
        hospital.setHospitalPhone(updatedHospital.getHospitalPhone());
        hospital.setHospitalEmail(updatedHospital.getHospitalEmail());
        hospital.setHospitalAddress(updatedHospital.getHospitalAddress());


        hospitalRepository.save(hospital); // 변경된 정보 저장
    }
    
    public void deleteHospital(String hospitalId) throws Exception {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new Exception("Hospital not found with ID: " + hospitalId));
        hospitalRepository.delete(hospital);
    }
	
	
    @Autowired
    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public List<Hospital> searchByHospitalNameOrAddress(String query) {
        // 병원 이름 또는 주소를 검색하는 메소드를 구현합니다.
    	return hospitalRepository.findByHospitalNameContainingAndApprovedIsTrueOrHospitalAddressContainingAndApprovedIsTrue(query, query);
    }
    
    
}