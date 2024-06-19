package com.port.petfit.user.member.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {

	@Autowired
	private PetRepository petRepository;

	// 기본 생성자 추가
	public PetService() {

	}

	// 반려동물 생성
	public void createPet(Pet pet) {
		petRepository.save(pet);
	}

	// 전체 반려동물 리스트
	public List<Pet> getAllPets() {
		return petRepository.findAll();
	}

}
