package com.port.petfit.user.member.board;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
public class BoardController {
 
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private BoardService boardService;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private ChatGPTService chatGPTService;
    
    @Autowired
    private BoardRepository boardRepository;
    
    @Autowired
    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    
    // 게시글 작성자에 현재 로그인 아이디 자동 입력
    @GetMapping("/my-page")
    public String myPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        model.addAttribute("currentUserId", currentUserId);

        return "my-page";
    }

    
    /*
     * 게시글 목록
     */
    @GetMapping("/list")
    public String list(@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        List<Board> boards = boardService.findAllWithComments();
        model.addAttribute("boardList", boards);
        return "/board/list";
    }
    
    /*
     * 게시글 상세 및 등록 폼 호출
     */
    @GetMapping({"", "/"})
    public String board(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        Board board = boardService.findBoardByIdx(idx);
        
        if (idx != 0) { // idx가 0이 아닌 경우에만 조회수 증가
            boardService.incrementViewCount(idx);
        }

        model.addAttribute("board", board);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        model.addAttribute("currentUserId", currentUserId);
        
        return "board/form";
    }
    
    /*
     * 게시글 생성
     */
    @PostMapping
    public ResponseEntity<?> postBoard(@RequestBody Board board) {
    	String currentUserId = boardService.getCurrentUserId(); // 현재 로그인된 사용자의 아이디 가져오기
        board.setWriter(currentUserId); // 현재 로그인된 사용자의 아이디를 게시글 작성자로 설정
        board.setCreatedDate(LocalDateTime.now());
        board.setUpdatedDate(LocalDateTime.now());
        boardRepository.save(board);
        
        // ChatGPT로부터 댓글 생성
        String prompt = "새로운 게시글이 생성되었습니다. 제목: " + board.getTitle() + ", 내용: " + board.getContent();
        String chatGPTComment = chatGPTService.getChatGPTResponse(prompt);

        // 댓글 추가
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setContent(chatGPTComment);
        comment.setWriter("ChatGPT");
        comment.setCreatedDate(LocalDateTime.now());
        commentService.postComment(board.getIdx(), comment);
        
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }
    
    @PutMapping("/{idx}")
    public ResponseEntity<?> putBoard(@PathVariable("idx") Long idx, @RequestBody Board board) {
        Board updateBoard = boardRepository.getOne(idx);
        updateBoard.setTitle(board.getTitle());
        updateBoard.setContent(board.getContent());
        updateBoard.setUpdatedDate(LocalDateTime.now());
        boardRepository.save(updateBoard);
        
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
    
    /*
     * 게시글 삭제
     */
    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteBoard(@PathVariable("idx") Long idx) {
        boardRepository.deleteById(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    
    }
    
    // 댓글
    @PostMapping("/{boardIdx}/comments")
    public ResponseEntity<?> postComment(@PathVariable("boardIdx") Long boardIdx, @RequestBody Comment comment) {
        commentService.postComment(boardIdx, comment);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }
    
    @GetMapping("/{boardIdx}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable("boardIdx") Long boardIdx) {
        List<Comment> comments = commentService.getCommentsByBoardIdx(boardIdx);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Long id, @RequestBody Comment updatedComment) {
        commentService.updateComment(id, updatedComment);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
