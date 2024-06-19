package com.port.petfit.user.member.board;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
 
@Getter
@Setter
@Entity
@Table(name = "board")
public class Board {
    
    /*
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     * 기본 키가 자동으로 할당되도록 설정하는 어노테이션
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    
    @Column
    private String title;
    
    @Column
    private String content;
    
    @Column
    private String writer;
    
    @Column
    private LocalDateTime createdDate;
    
    @Column
    private LocalDateTime updatedDate;
    
    @Builder
    public Board(Long idx, String title, String content, LocalDateTime createdDate, 
                    LocalDateTime updatedDate) {
        this.idx = idx;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
    
    // 기본 생성자
    public Board() {
    }
    
    public Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }
    
    // Board 엔티티에 댓글 관리 필드 추가
    @OneToMany(mappedBy = "board", cascade = {CascadeType.ALL})
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();
}
