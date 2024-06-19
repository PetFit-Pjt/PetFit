package com.port.petfit.user.member.board;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Comment {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdDate;
    
    private String writer;

    @ManyToOne
    @JsonBackReference
    private Board board;

    // 생성자
    public Comment( ) {
    	
    }
    
    // 매개변수가 있는 생성자
    public Comment(String content, String writer, Board board) {
        this.content = content;
        this.writer = writer;
        this.board = board;
        this.createdDate = LocalDateTime.now();
    }
}