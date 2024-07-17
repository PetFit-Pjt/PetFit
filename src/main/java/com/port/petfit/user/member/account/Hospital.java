package com.port.petfit.user.member.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.port.petfit.user.member.petdoc.reply.Reply;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "petfit_hospitals")
public class Hospital {

    @Id
    @Column(nullable = false, name = "hospital_Id")
    private String hospitalId;

    @Column(nullable = false)
    private String hospitalPw;

    @Column(nullable = false)
    private String hospitalName;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    private String hospitalPhone;

    @Column(nullable = false)
    private String hospitalAddress;

    @Column(nullable = false)
    private String hospitalEmail;

    @Column(nullable = false)
    private String businessRegistration;

    @Column(nullable = true)
    private String hospitalThumbnail;

    @Column(nullable = true)
    private String hospitalThumbnailUrl = "/images/default_thumbnail.png";  // 기본 이미지 URL 설정

    @Transient
    private MultipartFile hospitalThumbnailFile;

    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @Column(nullable = false)
    private boolean approved = false;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date a_registerDate;

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

	public String getHospitalThumbnail() {
		return hospitalThumbnail;
	}

	public void setHospitalThumbnail(String hospitalThumbnail) {
		this.hospitalThumbnail = hospitalThumbnail;
	}

	public String getHospitalThumbnailUrl() {
		return hospitalThumbnailUrl;
	}

	public void setHospitalThumbnailUrl(String hospitalThumbnailUrl) {
		this.hospitalThumbnailUrl = hospitalThumbnailUrl;
	}

	public MultipartFile getHospitalThumbnailFile() {
		return hospitalThumbnailFile;
	}

	public void setHospitalThumbnailFile(MultipartFile hospitalThumbnailFile) {
		this.hospitalThumbnailFile = hospitalThumbnailFile;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Date getA_registerDate() {
		return a_registerDate;
	}

	public void setA_registerDate(Date a_registerDate) {
		this.a_registerDate = a_registerDate;
	}
    
    
    @Column(nullable = false)
    private String doctorNames; // 의사 목록

	public void setDoctorNames(String doctorNames2) {
		// TODO Auto-generated method stub
		
	}
}
