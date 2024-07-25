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
    
    @Column
    private Integer viewCount = 0; 
    
    @Builder
    public Board(Long idx, String title, String content, LocalDateTime createdDate, 
                    LocalDateTime updatedDate, Integer viewCount) {
        this.idx = idx;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.viewCount = viewCount != null ? viewCount : 0;
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
        this.viewCount = 0; // 게시글 생성 시 조회수 0으로 설정
    }
    
    // 댓글 개수를 카운팅
    public int getCommentCount() {
        return comments != null ? comments.size() : 0;
    }
    
    // Board 엔티티에 댓글 관리 필드 추가
    @OneToMany(mappedBy = "board", cascade = {CascadeType.ALL})
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
    
    
}
