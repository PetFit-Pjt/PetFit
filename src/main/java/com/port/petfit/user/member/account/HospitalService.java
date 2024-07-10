package com.port.petfit.user.member.account;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        if (hospital != null && passwordEncoder.matches(hospitalPw, hospital.getHospitalPw())) {
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
    public void updateHospital(String hospitalId, Hospital updatedHospital, String newPassword) {
        Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
        if (hospital != null) {
            // 새 비밀번호가 제공된 경우에만 비밀번호 업데이트
            if (newPassword != null && !newPassword.isEmpty()) {
                hospital.setHospitalPw(passwordEncoder.encode(newPassword));
            }
            
            hospital.setHospitalPhone(updatedHospital.getHospitalPhone());
            hospital.setHospitalEmail(updatedHospital.getHospitalEmail());
            hospital.setHospitalAddress(updatedHospital.getHospitalAddress());
            // 필요한 다른 필드들도 업데이트

            hospitalRepository.save(hospital);
        }
    }

    public void deleteHospital(String hospitalId) throws Exception {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new Exception("Hospital not found with ID: " + hospitalId));
        hospitalRepository.delete(hospital);
    }

    @Autowired
    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public Page<Hospital> searchByHospitalNameOrAddress(String query, Pageable pageable) {
        return hospitalRepository.findByHospitalNameContainingAndApprovedIsTrueOrHospitalAddressContainingAndApprovedIsTrue(query, query, pageable);
    }

    public Page<Hospital> getAllHospitals(Pageable pageable) {
        return hospitalRepository.findAll(pageable);
    }

    public Hospital getHospitalById(String hospitalId) {
        return hospitalRepository.findByHospitalId(hospitalId);
    }
    
    public Page<Hospital> searchByDoctorNames(String query, PageRequest pageRequest) {
        return hospitalRepository.findByDoctorNamesContaining(query, pageRequest);
    }
}
