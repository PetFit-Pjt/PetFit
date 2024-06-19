package com.port.petfit.user.member.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	List<Board> findTop5ByOrderByCreatedDateDesc();
	
	Optional<Board> findFirstByOrderByCreatedDateDesc();

}