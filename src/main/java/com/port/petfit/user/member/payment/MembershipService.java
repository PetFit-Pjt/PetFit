package com.port.petfit.user.member.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.port.petfit.user.member.account.User;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;

    private final PaymentRepository paymentRepository;

    @Autowired
    public MembershipService(MembershipRepository membershipRepository, PaymentRepository paymentRepository) {
        this.membershipRepository = membershipRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void processMembershipPayment(User user, Membership membership, Payment payment) {
        // 멤버십 정보 저장
        Membership savedMembership = membershipRepository.save(membership);

        // 결제 내역 저장
        payment.setUser(user);
        payment.setMembershipId(savedMembership.getMembershipId());
        paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }
}
