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
        String encodedPassword = passwordEncoder.encode(hospital.getHospitalPw());
        hospital.setHospitalPw(encodedPassword);

        // 기본 이미지 URL 설정
        hospital.setHospitalThumbnailUrl("/images/default_thumbnail.png");

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
            String uploadDir = "C:/Hospitals/mit/Downloads/hospital_thumbnail";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = hospital.getHospitalId() + "_" + UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            File dest = new File(filePath);
            file.transferTo(dest);

            String fileUrl = "/hospital_thumbnail/" + fileName;

            hospital.setHospitalThumbnail(filePath);
            hospital.setHospitalThumbnailUrl(fileUrl);
            hospitalRepository.save(hospital);
        } catch (IOException e) {
            throw new RuntimeException("이미지 파일 저장 중 오류가 발생했습니다.", e);
        }
    }

    public Hospital authenticate(String hospitalId, String hospitalPw) {
        Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
        if (hospital != null && PasswordUtil.matches(hospitalPw, hospital.getHospitalPw())) {
            return hospital;
        }
        return null;
    }

    public String getCurrentHospitalId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    @Transactional
    public void updateHospital(Hospital updatedHospital) {
        String hospitalId = getCurrentHospitalId();
        Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
        
        hospital.setHospitalPw(passwordEncoder.encode(updatedHospital.getHospitalPw()));
        hospital.setHospitalPhone(updatedHospital.getHospitalPhone());
        hospital.setHospitalEmail(updatedHospital.getHospitalEmail());
        hospital.setHospitalAddress(updatedHospital.getHospitalAddress());

        hospitalRepository.save(hospital);
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
        return hospitalRepository.findByHospitalNameContainingAndApprovedIsTrueOrHospitalAddressContainingAndApprovedIsTrue(query, query);
    }
}
