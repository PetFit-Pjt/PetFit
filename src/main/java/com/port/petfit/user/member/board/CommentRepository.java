package com.port.petfit.user.member.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 게시글의 idx를 기준으로 댓글을 조회하는 메서드
    List<Comment> findByBoardIdx(Long boardIdx);	
    
}
