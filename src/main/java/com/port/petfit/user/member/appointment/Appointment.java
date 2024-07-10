package com.port.petfit.user.member.appointment;

import org.springframework.format.annotation.DateTimeFormat;

import com.port.petfit.user.member.account.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "petfit_appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer appointmentId;  	// 예약 고유 번호
    
	@ManyToOne
	@JoinColumn(nullable = false, name = "user_Id") // User 테이블의 user_Id 필드를 외래키로 참조
	private User user;
    
    @Column(nullable = false)
    private String hospitalName;		// 병원명
    
    @Column(nullable = false)
    private String petName;				// 반려동물 이름
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(nullable = true)
    private String appointmentDateTime;   // 예약일자
    
    @Column(nullable = false)
    private String medicalNotes;		// 희망 진료 내용

	@Column(nullable = false)
	private boolean approved = false; // 승인속성
    
    // 기본 생성자 추가
    public Appointment() {
    	
    }

	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getAppointmentDateTime() {
		return appointmentDateTime;
	}

	public void setAppointmentDateTime(String appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}

	public String getMedicalNotes() {
		return medicalNotes;
	}

	public void setMedicalNotes(String medicalNotes) {
		this.medicalNotes = medicalNotes;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
    
    
    
    
    
}