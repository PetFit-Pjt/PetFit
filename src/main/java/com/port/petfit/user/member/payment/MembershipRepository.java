package com.port.petfit.user.member.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.port.petfit.user.member.account.User;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
	
	Membership findByUser(User user);

	Membership findByUserAndMembershipstatus(User user, String string);

	
}
