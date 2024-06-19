package com.port.petfit.user.member.petdoc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetdocService {
	
	@Autowired
	private PetdocRepository petdocRepository;
	
    // 기본 생성자 추가
    public PetdocService() {
    	
    }
    
	// 글 생성
    public void createPetdoc(Petdoc petdoc) {
    	petdocRepository.save(petdoc);
    }
    
    // 수정 페이지 보여주기
    public Petdoc getPetdocById(Integer id) {
        return petdocRepository.findById(id).orElse(null);
    }

    // 글 수정
    public void editPetdoc(Petdoc petdoc) {
        petdocRepository.save(petdoc);
    }

    // 글 삭제
    public void deletePetdoc(Integer id) {
        petdocRepository.deleteById(id);
    }
    
    
    // 전체 글 리스트
    public List<Petdoc> getAllPetdocs() {
        return petdocRepository.findAll();
    }
    
    // 내가 쓴 글 목록
    public List<Petdoc> getPetdocsByUserName(String userName) {
        return petdocRepository.findByUserName(userName);
    }
    
}

