package com.port.petfit.user.member.payment;

public class PaymentVerificationRequest {
	private String impUid;
	
	private Double paidAmount;
	
	private int durationMonths;

	// 생성자
	public PaymentVerificationRequest(String impUid, Double paidAmount, int durationMonths) {
		this.impUid = impUid;		
		this.paidAmount = paidAmount;		
		this.durationMonths = durationMonths;
	}

	public String getImpUid() {
		return impUid;
	}

	public void setImpUid(String impUid) {
		this.impUid = impUid;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public int getDurationMonths() {
		return durationMonths;
	}

	public void setDurationMonths(int durationMonths) {
		this.durationMonths = durationMonths;
	}

}