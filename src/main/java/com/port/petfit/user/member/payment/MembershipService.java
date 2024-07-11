package com.port.petfit.user.member.payment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.port.petfit.user.member.account.User;

@Service
public class MembershipService {
	
	private static final Logger logger = LoggerFactory.getLogger(MembershipService.class);

    private final MembershipRepository membershipRepository;

    private final PaymentRepository paymentRepository;

    @Autowired
    public MembershipService(MembershipRepository membershipRepository, PaymentRepository paymentRepository) {
        this.membershipRepository = membershipRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void processMembershipPayment(User user, int durationMonths, Payment payment) {
        // 사용자의 현재 활성 멤버십 조회
        Optional<Membership> currentMembershipOpt = membershipRepository.findByUser(user);
        
        Date today = new Date(); // 오늘 날짜 캐싱
        if (currentMembershipOpt.isPresent()) {
            Membership currentMembership = currentMembershipOpt.get();
            currentMembership.extendMembership(durationMonths); // 멤버십 기간 연장
            currentMembership.setPayment(payment); // 결제 정보 갱신
            membershipRepository.save(currentMembership);
        } else {
            // 멤버십이 없는 경우 새로 시작
            Membership newMembership = createNewMembership(user, durationMonths, payment, today);
            membershipRepository.save(newMembership);
        }

        // 결제 내역 저장
        payment.setUser(user);
        paymentRepository.save(payment);
    }

    private Membership createNewMembership(User user, int durationMonths, Payment payment, Date startDate) {
        Membership newMembership = new Membership();
        newMembership.setUser(user);
        newMembership.setPayment(payment);
        newMembership.setStartDate(startDate);
        newMembership.setDurationMonths(durationMonths);
        newMembership.setMembershipstatus("Active");
        newMembership.calculateEndDate(); // 종료 날짜 계산
        return newMembership;
    }

    @Transactional(readOnly = true)
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }
}
