package com.port.petfit.user.member.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	// 필요한 경우 사용자 정의 쿼리 메소드를 추가할 수 있습니다
    User findByUserName(String userName);
    
    User findByUserIdAndUserPw(String userId, String userPw);
    
    User findByUserId(String userId);

    User findByUserEmail(String userEmail);
}
