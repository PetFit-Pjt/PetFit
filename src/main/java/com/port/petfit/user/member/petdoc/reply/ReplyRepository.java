package com.port.petfit.user.member.petdoc.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    // 추가적인 쿼리가 필요한 경우 여기에 작성
}