package com.port.petfit.user.member.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentVerificationRepository extends JpaRepository<PaymentVerificationEntity, Long> {

}
