package com.port.petfit.user.member.customer;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "petfit_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer customerNo;  			// 게시글 고유 번호
    
    @Column(nullable = false)
    private String title;				// 글 제목
    
    @Column(nullable = false)
    private String content;				// 문의 내용

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date registerDate;       	// 작성일자

    

    // 기본 생성자 추가
    public Customer() {
    	
    }
}