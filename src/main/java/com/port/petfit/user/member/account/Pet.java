package com.port.petfit.user.member.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "petfit_pets") // 데이터베이스 테이블 이름이 "petfit_pets"
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name = "pet_Id")
	private Integer petId; // 반려동물 고유 번호
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "user_Id") // User 테이블의 user_Id 필드를 외래키로 참조
	private User user;

	@Column(nullable = false)
	private String petName; // 반려동물 이름

	@Column(nullable = false)
	private String gender; // 성별

	@Column(nullable = false)
	private Integer petAge; // 나이

	@Column(nullable = false)
	private String vaccination; // 예방접종 유무

	@Column
	private String disease; // 질병

	@Column(nullable = false)
	private String species; // 종 

	@Column(nullable = false)
	private String breed;	// 품종

	@Column(name = "chip_Num")
	private String chipNum;

	public Pet() {

	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}
	
	public String getChipNum() {
		return chipNum;
	}

	public void setChipNum(String chipNum) {
		this.chipNum = chipNum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getPetAge() {
		return petAge;
	}

	public void setPetAge(Integer petAge) {
		this.petAge = petAge;
	}

	public String getVaccination() {
		return vaccination;
	}

	public void setVaccination(String vaccination) {
		this.vaccination = vaccination;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}
}