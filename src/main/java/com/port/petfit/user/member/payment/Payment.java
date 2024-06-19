package com.port.petfit.user.member.payment;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.port.petfit.user.member.account.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "petfit_payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentNum;

    @Column(nullable = false)
    private double amount; // 결제 금액

    @Column(nullable = false)
    private String paymentMethod = "Credit Card"; // 결제 방식

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date paymentDate; // 결제 일자

    @Column(nullable = false, unique = true)
    private String impUid; // 아임포트 결제 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user; // 결제한 사용자

    private Long membershipId;

    public Long getMembershipId() {
        return membershipId;
    }
    
    public void setMembershipId(Long membershipId) {
        this.membershipId = membershipId;
    }
    
    
	public Long getPaymentId() {
		return paymentNum;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentNum = paymentId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getImpUid() {
		return impUid;
	}

	public void setImpUid(String impUid) {
		this.impUid = impUid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    // 생성자 및 게터/세터 생략
    
    
}
