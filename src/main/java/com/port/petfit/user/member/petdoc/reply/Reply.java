package com.port.petfit.user.member.petdoc.reply;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.port.petfit.user.member.account.Hospital;
import com.port.petfit.user.member.petdoc.Petdoc;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "petfit_Replies")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer replyId;            // 답글 고유 번호
    
    @Column(nullable = false)
    private String content;             // 답글 내용
    
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;           // 답글 작성일자
    
    @Column(nullable = false)
    @UpdateTimestamp
    private Date updateDate;           // 답글 수정일자
    
    @ManyToOne
    private Petdoc petdoc;             // 해당 게시글에 대한 답글

    @ManyToOne
    @JoinColumn(name = "hospital_Id", nullable = false)
    private Hospital hospital;         
    
    // 기본 생성자 추가
    public Reply() {
        
    }

	public void setUserName(String userName) {
		// TODO Auto-generated method stub
		
	}

	public Integer getReplyId() {
		return replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Petdoc getPetdoc() {
		return petdoc;
	}

	public void setPetdoc(Petdoc petdoc) {
		this.petdoc = petdoc;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	
}