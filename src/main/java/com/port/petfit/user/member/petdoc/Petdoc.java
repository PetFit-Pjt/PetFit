package com.port.petfit.user.member.petdoc;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.port.petfit.user.member.petdoc.reply.Reply;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "petfit_Petdocs")
public class Petdoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer PetdocId;  			// 게시글 고유 번호
    
    @Column(nullable = false)
    private String title;				// 글 제목
    
    @Column(nullable = false)
    private String details;				// 문의 내용
    
    @Column(nullable = false)
    private String userName;			// 사용자 이름(글 작성자)

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date registerDate;       	// 작성일자

    @OneToMany(mappedBy = "petdoc")
    private List<Reply> replies;        // 해당 게시글에 대한 답글 목록

    

    // 기본 생성자 추가
    public Petdoc() {
    	
    }



	public Integer getPetdocId() {
		return PetdocId;
	}



	public void setPetdocId(Integer petdocId) {
		PetdocId = petdocId;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getDetails() {
		return details;
	}



	public void setDetails(String details) {
		this.details = details;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public Date getRegisterDate() {
		return registerDate;
	}



	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}



	public List<Reply> getReplies() {
		return replies;
	}



	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
    
    
}