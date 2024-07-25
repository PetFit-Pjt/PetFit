package com.port.petfit.user.member.board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    Page<Board> findBoardList(Pageable pageable);
    Board findBoardByIdx(Long idx);    
    String getCurrentUserId();
	void incrementViewCount(Long idx);
	List<Board> findAllWithComments();
}
