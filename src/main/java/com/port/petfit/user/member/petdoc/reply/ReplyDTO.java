package com.port.petfit.user.member.petdoc.reply;

import lombok.Data;

@Data
public class ReplyDTO {
    private String content; // 답글 내용
    private Integer parentPostId; // 부모 게시물의 ID
    private String userName; // 사용자 이름 필드 추가
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getParentPostId() {
		return parentPostId;
	}

	public void setParentPostId(Integer parentPostId) {
		this.parentPostId = parentPostId;
	}
    
    
}