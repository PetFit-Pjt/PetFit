package com.port.petfit.user.member.account;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "petfit_admins") // 데이터베이스 테이블 이름이 "petfit_pets"
public class Admin {

	@Id
	@Column(nullable = false, name = "admin_Id")
	private String adminId; // 아이디

	@Column(nullable = false)
	private String adminPw; // 비밀번호

	@Column(nullable = false)
	private String adminName; // 생년월일 (YYYYMMDD)

	@Column(nullable = false)
	private String adminBirth; // 생년월일 (YYYYMMDD)

	@Column(nullable = false)
	private String adminPhone; // 전화번호

	@Column(nullable = false)
	private String adminAddress; // 주소

	@Column(nullable = false)
	private String adminEmail; // 이메일

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date a_registerDate;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminPw() {
		return adminPw;
	}

	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPhone() {
		return adminPhone;
	}

	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}

	public String getAdminAddress() {
		return adminAddress;
	}

	public void setAdminAddress(String adminAddress) {
		this.adminAddress = adminAddress;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public Date getA_registerDate() {
		return a_registerDate;
	}

	public void setA_registerDate(Date a_registerDate) {
		this.a_registerDate = a_registerDate;
	}

	public String getAdminBirth() {
		return adminBirth;
	}

	public void setAdminBirth(String adminBirth) {
		this.adminBirth = adminBirth;
	}

	public String getAdminName() {
		return adminName;
	}

}
