package com.port.petfit.user.member.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Integer> {
	Pet findByChipNum(String chipNum);

	boolean existsByChipNum(String chipNum);
    
}