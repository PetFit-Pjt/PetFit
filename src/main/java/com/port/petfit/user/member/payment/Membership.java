package com.port.petfit.user.member.payment;

import java.util.Calendar;
import java.util.Date;

import com.port.petfit.user.member.account.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "petfit_memberships")
public class Membership {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long membershipId;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date startDate; // 멤버십 시작 날짜

	@Column(nullable = false)
	private int durationMonths; // 멤버십 유효 기간 (월 단위)

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user; // 결제한 사용자

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paymentId")
	private Payment payment; // 해당 멤버십을 활성화한 결제

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date endDate; // 멤버십 종료 날짜, 계산에 의해 결정

	@Column(nullable = false)
	private String membershipstatus; // 멤버십 상태

	// 기본 생성자
	public Membership() {
	}

	// 게터 및 세터
	public Long getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(Long membershipId) {
		this.membershipId = membershipId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

	public int getDurationMonths() {
		return durationMonths;
	}

	public void setDurationMonths(int durationMonths) {
		this.durationMonths = durationMonths;
		calculateEndDate(); // 종료 날짜 재계산
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public void extendMembership(int additionalMonths) {
        if (isMembershipActive()) {
            // 현재 멤버십이 유효한 경우, endDate를 기준으로 연장
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.MONTH, additionalMonths);
            this.endDate = cal.getTime();
        } else {
            // 현재 멤버십이 만료된 경우, 오늘 날짜를 기준으로 연장
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, additionalMonths);
            this.endDate = cal.getTime();
        }
        this.durationMonths += additionalMonths; // 전체 기간 추가
        updateMembershipStatus(); // 멤버십 상태 업데이트
    }

    // 멤버십이 유효한지 판단하는 메서드
    public boolean isMembershipActive() {
        return endDate != null && endDate.after(new Date());
    }

    // 종료 날짜를 계산하는 내부 메소드
    void calculateEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate); // 새로 설정된 startDate 사용
        cal.add(Calendar.MONTH, durationMonths);
        this.endDate = cal.getTime();
    }

    // 멤버십 상태를 업데이트하는 메서드
    public void updateMembershipStatus() {
        if (isMembershipActive()) {
            this.membershipstatus = "Active";
        } else {
            this.membershipstatus = "Expired";
        }
    }

	public String getMembershipstatus() {
		return membershipstatus;
	}

	public void setMembershipstatus(String membershipstatus) {
		this.membershipstatus = membershipstatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	

}