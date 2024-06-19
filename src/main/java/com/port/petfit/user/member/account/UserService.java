package com.port.petfit.user.member.account;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.port.petfit.config.PasswordUtil;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private EmailService emailService; // 이메일 서비스 추가 가정

	public void registerUser(User user) {
		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(user.getUserPw());

		// 암호화된 비밀번호를 User 객체에 설정
		user.setUserPw(encodedPassword);

		// 데이터베이스에 사용자 정보 저장
		userRepository.save(user);
	}

	public User authenticate(String userId, String userPw) {
		// 사용자 정보 검색
		User user = userRepository.findByUserId(userId);

		// 사용자가 존재하고 비밀번호가 일치하는지 확인
		if (user != null && PasswordUtil.matches(userPw, user.getUserPw())) {
			return user; // 인증 성공 시 사용자 반환
		}

		// 인증 실패 시 null 반환
		return null;
	}
	
	public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // 사용자명을 ID로 사용하는 경우
        }
        return null;
    }
    
    @Transactional
    public void updateUser(User updatedUser) {
        String userId = getCurrentUserId(); // 현재 사용자 ID 가져오기
        User user = userRepository.findByUserId(userId);
        
        // 받은 객체에서 가져온 정보로 업데이트
        user.setUserPw(passwordEncoder.encode(updatedUser.getUserPw())); // 비밀번호 암호화하여 업데이트
        user.setUserPhone(updatedUser.getUserPhone());
        user.setUserEmail(updatedUser.getUserEmail());
        user.setUserAddress(updatedUser.getUserAddress());
        user.setUserBirth(updatedUser.getUserBirth());

        userRepository.save(user); // 변경된 정보 저장
    }
    
    @Transactional
    public void updateUser2(String userId, User updatedUser) {
        // 특정 사용자의 정보를 가져옴
        User user = userRepository.findByUserId(userId);
        
        if (user != null) {
            // 받은 객체에서 가져온 정보로 업데이트
            user.setUserPw(passwordEncoder.encode(updatedUser.getUserPw())); // 비밀번호 암호화하여 업데이트
            user.setUserPhone(updatedUser.getUserPhone());
            user.setUserEmail(updatedUser.getUserEmail());
            user.setUserAddress(updatedUser.getUserAddress());
            user.setUserBirth(updatedUser.getUserBirth());

            userRepository.save(user); // 변경된 정보 저장
        } else {
            // 사용자가 존재하지 않을 경우에 대한 처리
            // 예를 들어, 예외를 던지거나 적절한 응답을 반환할 수 있음
        }
    }
    
    public void deleteUser(String userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found with ID: " + userId));
        userRepository.delete(user);
    }

    // 임시 비밀번호 발급 및 이메일 발송 기능
    @Transactional
    public boolean recoverAccount(String email) {
        User user = userRepository.findByUserEmail(email); // 이메일로 사용자 검색 (findByEmail 메소드 추가 필요)
        if (user == null) {
            return false;
        }

        // 임시 비밀번호 생성
        String tempPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        user.setUserPw(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        // 이메일 발송 로직
        String subject = "Your account recovery details";
        String text = "Dear " + user.getUserName() + ",\nYour username is " + user.getUserId() + 
                      " and your temporary password is " + tempPassword + 
                      ". Please change your password upon login.";
        emailService.send(user.getUserEmail(), subject, text);
        return true;
    }	
    
}
