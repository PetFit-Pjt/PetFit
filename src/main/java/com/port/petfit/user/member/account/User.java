package com.port.petfit.user.member.account;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "petfit_users") // 데이터베이스 테이블 이름이 "petfit_users"
public class User {

	@Id
	@Column(nullable = false, name = "user_Id")
	private String userId; // 아이디

	@Column(nullable = false)
	private String userPw; // 비밀번호

	@Column(nullable = false)
	private String userName; // 이름

	@Column(nullable = false)
	private String userBirth; // 생년월일 (YYYYMMDD)

	@Column(nullable = false)
	private String userEmail; // 이메일

	@Column(nullable = false)
	private String userAddress; // 주소

	@Column(nullable = false)
	private String userPhone; // 전화번호

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date u_registerDate;

	// 기본 생성자
	public User() {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserBirth() {
		return userBirth;
	}

	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

}
