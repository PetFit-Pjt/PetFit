package com.port.petfit.user.member.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.port.petfit.config.PasswordUtil;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void registerAdmin(Admin admin) {
		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(admin.getAdminPw());

		// 암호화된 비밀번호를 User 객체에 설정
		admin.setAdminPw(encodedPassword);

		// 데이터베이스에 사용자 정보 저장
		adminRepository.save(admin);
	}

	public Admin authenticate(String adminId, String adminPw) {
		// 사용자 정보 검색
		Admin admin = adminRepository.findByAdminId(adminId);

		// 사용자가 존재하고 비밀번호가 일치하는지 확인
		if (admin != null && PasswordUtil.matches(adminPw, admin.getAdminPw())) {
			return admin; // 인증 성공 시 사용자 반환
		}

		// 인증 실패 시 null 반환
		return null;
	}
}