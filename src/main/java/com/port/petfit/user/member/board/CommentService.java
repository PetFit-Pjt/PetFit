package com.port.petfit.user.member.board;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ChatGPTService chatGPTService;

    @Autowired
    public CommentService(BoardRepository boardRepository, CommentRepository commentRepository, ChatGPTService chatGPTService) {
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
        this.chatGPTService = chatGPTService;
    }

    public void postComment(Long boardIdx, Comment comment) {
        Board board = boardRepository.findById(boardIdx)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        comment.setCreatedDate(LocalDateTime.now());
        comment.setBoard(board);
        commentRepository.save(comment);
    }

    public List<Comment> getComments(Long boardIdx) {
        Board board = boardRepository.findById(boardIdx)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return board.getComments();
    }
    
    public List<Comment> getCommentsByBoardIdx(Long boardIdx) {
        return commentRepository.findByBoardIdx(boardIdx);
    }
    
    public void updateComment(Long id, Comment updatedComment) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        comment.setContent(updatedComment.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public void addChatGPTComment(Long boardIdx, String prompt) {
        String chatGPTComment = chatGPTService.getChatGPTResponse(prompt);
        Comment comment = new Comment();
        comment.setContent(chatGPTComment);
        comment.setWriter("ChatGPT");
        postComment(boardIdx, comment);
    }
}
