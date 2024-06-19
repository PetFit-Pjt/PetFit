package com.port.petfit.user.member.petdoc.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.port.petfit.user.member.account.Hospital;
import com.port.petfit.user.member.petdoc.Petdoc;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public void createReply(ReplyDTO replyDTO) {
        Reply reply = new Reply();
        reply.setContent(replyDTO.getContent());
        // 부모 게시물의 ID를 사용하여 부모 게시물 정보 설정
        Petdoc parentPost = new Petdoc();
        parentPost.setPetdocId(replyDTO.getParentPostId());
        reply.setPetdoc(parentPost);
        // 병원 정보 설정 (여기서는 임시로 고정값으로 설정)
        Hospital hospital = new Hospital();
        hospital.setHospitalId("병원ID");
        reply.setHospital(hospital);
        replyRepository.save(reply);
    }
    
}