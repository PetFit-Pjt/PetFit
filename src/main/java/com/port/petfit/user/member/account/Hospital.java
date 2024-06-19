package com.port.petfit.user.member.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.port.petfit.user.member.petdoc.reply.Reply;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "petfit_hospitals") // 데이터베이스 테이블 이름이 "petfit_hospitals"
public class Hospital {

	@Id
	@Column(nullable = false, name = "hospital_Id")
	private String hospitalId; // 아이디

	@Column(nullable = false)
	private String hospitalPw; // 비밀번호

	@Column(nullable = false)
	private String hospitalName; // 상호명

	@Column(nullable = false)
	private String startTime; // 영업시작시간

	@Column(nullable = false)
	private String endTime; // 영업종료시간

	@Column(nullable = false)
	private String hospitalPhone; // 전화번호

	@Column(nullable = false)
	private String hospitalAddress; // 주소

	@Column(nullable = false)
	private String hospitalEmail; // 이메일

	@Column(nullable = false)
	private String businessRegistration; // 사업자 등록증

    @Column(nullable = true)
    private String hospitalThumbnail;
	
	@Transient // 데이터베이스에 저장되지 않는 속성임을 명시
    private MultipartFile hospitalThumbnailFile; // 이미지를 업로드하기 위한 MultipartFile 타입의 속성

	
    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();
	
	@Column(nullable = false)
	private boolean approved = false; // 승인속성

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date a_registerDate;

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalPw() {
		return hospitalPw;
	}

	public void setHospitalPw(String hospitalPw) {
		this.hospitalPw = hospitalPw;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHospitalPhone() {
		return hospitalPhone;
	}

	public void setHospitalPhone(String hospitalPhone) {
		this.hospitalPhone = hospitalPhone;
	}

	public String getHospitalAddress() {
		return hospitalAddress;
	}

	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
	}

	public String getHospitalEmail() {
		return hospitalEmail;
	}

	public void setHospitalEmail(String hospitalEmail) {
		this.hospitalEmail = hospitalEmail;
	}

	public String getBusinessRegistration() {
		return businessRegistration;
	}

	public void setBusinessRegistration(String businessRegistration) {
		this.businessRegistration = businessRegistration;
	}

    public void setHospitalThumbnail(String hospitalThumbnail) {
        this.hospitalThumbnail = hospitalThumbnail;
    }
	
    public MultipartFile getHospitalThumbnailFile() {
        return hospitalThumbnailFile;
    }

    public void setHospitalThumbnailFile(MultipartFile hospitalThumbnailFile) {
        this.hospitalThumbnailFile = hospitalThumbnailFile;
    }
	
	public Date getA_registerDate() {
		return a_registerDate;
	}

	public void setA_registerDate(Date a_registerDate) {
		this.a_registerDate = a_registerDate;
	}

}