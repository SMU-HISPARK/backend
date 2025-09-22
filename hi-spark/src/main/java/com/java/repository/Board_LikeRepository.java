package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.dto.Board;
import com.java.dto.Board_Like;
import com.java.entity.Member;

@Repository
public interface Board_LikeRepository extends JpaRepository<Board_Like, Integer> {

	Optional<Board_Like> findByBoardAndMember(Board board, Member member);

	long countByBoard(Board board);

	void deleteByBoardBno(int bno);
}