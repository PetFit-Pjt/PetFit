package com.port.petfit.user.member.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {
 
    @Autowired
    private BoardRepository boardRepository;
    
    @Override
    public Page<Board> findBoardList(Pageable pageable) {
//        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
//            pageable.getPageSize());
        return boardRepository.findAll(pageable);
    }
 
    @Override
    public Board findBoardByIdx(Long idx) {
        return boardRepository.findById(idx).orElse(new Board());
    }

    @Override
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


    // 댓글과 함께 게시글 목록 조회
    @Override
    public List<Board> findAllWithComments() {
        List<Board> boards = boardRepository.findAll();
        boards.forEach(board -> board.getComments().size()); // 댓글을 강제로 로드
        return boards;
    }
    
    @Override
    public void incrementViewCount(Long idx) {
        Board board = boardRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + idx));
        Integer currentViewCount = board.getViewCount() != null ? board.getViewCount() : 0;
        board.setViewCount(currentViewCount + 1);
        boardRepository.save(board);
    }
    
}
