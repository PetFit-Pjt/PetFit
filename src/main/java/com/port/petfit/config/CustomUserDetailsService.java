package com.port.petfit.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.port.petfit.user.member.account.Admin;
import com.port.petfit.user.member.account.AdminRepository;
import com.port.petfit.user.member.account.Hospital;
import com.port.petfit.user.member.account.HospitalRepository;
import com.port.petfit.user.member.account.User;
import com.port.petfit.user.member.account.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private HospitalRepository hospitalRepository;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    	 if (userId == null || userId.trim().isEmpty()) {
             throw new UsernameNotFoundException("User ID is empty");
         }

         System.out.println("Attempting to load user: " + userId);  // 로그 추가

         // 일반 사용자 조회
         User user = userRepository.findByUserId(userId);
         if (user != null) {
             return new org.springframework.security.core.userdetails.User(
                 user.getUserId(),
                 user.getUserPw(),
                 Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
             );
         }

         // 관리자 조회
         Admin admin = adminRepository.findByAdminId(userId);
         if (admin != null) {
             return new org.springframework.security.core.userdetails.User(
                 admin.getAdminId(),
                 admin.getAdminPw(),
                 Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
             );
         }
         
      // 병원 조회
         Hospital hospital = hospitalRepository.findByHospitalId(userId);
         if (hospital != null) {
             if (!hospital.isApproved()) {
                 throw new UsernameNotFoundException("Hospital account is not approved yet.");
             }
             return new org.springframework.security.core.userdetails.User(
                 hospital.getHospitalId(),
                 hospital.getHospitalPw(),
                 Collections.singletonList(new SimpleGrantedAuthority("ROLE_HOSPITAL"))
             );
         }
         
      // 두 리포지토리 모두에서 사용자를 찾을 수 없는 경우
         throw new UsernameNotFoundException("User not found with userId: " + userId);       
         
    }
}

