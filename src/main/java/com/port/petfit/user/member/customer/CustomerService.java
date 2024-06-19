package com.port.petfit.user.member.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
    // 기본 생성자 추가
    public CustomerService() {    	
    }
    
//	// 글 생성
//    public void createNotioce(Notioce notioce) {
//    	notioceRepository.save(notioce);
//    }

    
    // 전체 글 리스트
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }
    


}

