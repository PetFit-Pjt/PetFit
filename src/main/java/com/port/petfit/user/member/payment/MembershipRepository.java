package com.port.petfit.user.member.payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.port.petfit.user.member.account.User;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
	
	Optional<Membership> findByUser(User user); // Optional로 변경하여 처리

	Optional<Membership> findByUserAndMembershipstatus(User user, String membershipStatus);

	// 사용자의 최근 멤버십을 찾는 쿼리, endDate를 기준으로 내림차순 정렬하여 가장 최근 것을 가져옴
	Optional<Membership> findTopByUserOrderByEndDateDesc(User user);
	
	
}
