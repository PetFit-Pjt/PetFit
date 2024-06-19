package com.port.petfit.user.member.petdoc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetdocRepository extends JpaRepository<Petdoc, Integer> {

	List<Petdoc> findByUserName(String userName);
}