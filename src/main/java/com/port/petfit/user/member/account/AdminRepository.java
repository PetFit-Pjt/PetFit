package com.port.petfit.user.member.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    // 필요한 경우 사용자 정의 쿼리 메소드를 추가할 수 있습니다
    Admin findByAdminName(String adminName);
    
    Admin findByAdminIdAndAdminPw(String adminId, String adminPw);
    
    Admin findByAdminId(String adminId);
}
