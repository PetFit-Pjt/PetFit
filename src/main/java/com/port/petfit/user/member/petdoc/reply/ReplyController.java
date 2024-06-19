package com.port.petfit.user.member.petdoc.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.port.petfit.user.member.account.Hospital;
import com.port.petfit.user.member.account.HospitalRepository;

@RestController
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private HospitalRepository hospitalRepository;

    @PostMapping("/replies")
    public ResponseEntity<String> createReply(@RequestBody ReplyDTO replyDTO) {
        try {
            // 인증된 사용자 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         // 사용자가 병원인지 확인하고, 병원 정보 가져오기
            Object principal = authentication.getPrincipal();
            Hospital hospital = null;
            if (principal instanceof Hospital) {
                hospital = (Hospital) principal;
            } else {
                // 사용자가 병원이 아닌 경우 에러 처리 또는 다른 처리 로직 추가
            }

            // 답글 생성
            Reply reply = new Reply();
            reply.setContent(replyDTO.getContent());
            reply.setUserName(replyDTO.getUserName());
            reply.setHospital(hospital);
            reply.setPetdoc(null/* 부모 게시글 엔티티 설정 */);

            replyService.createReply(replyDTO);
            return ResponseEntity.ok("답글이 작성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답글 작성에 실패했습니다.");
        }
    }
}