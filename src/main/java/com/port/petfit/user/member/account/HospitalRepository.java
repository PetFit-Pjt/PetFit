package com.port.petfit.user.member.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, String> {
    Hospital findByHospitalName(String hospitalName);
    
    Hospital findByHospitalIdAndHospitalPw(String hospitalId, String hospitalPw);
    
    Hospital findByHospitalId(String hospitalId);
    
    List<Hospital> findByHospitalNameContainingAndApprovedIsTrueOrHospitalAddressContainingAndApprovedIsTrue(String hospitalName, String hospitalAddress);
}