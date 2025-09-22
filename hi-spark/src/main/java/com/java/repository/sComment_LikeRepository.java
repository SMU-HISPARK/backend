package com.java.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.sComment; // sComment로 변경
import com.java.dto.sComment_Like; // sComment_Like로 변경
import com.java.entity.Member;

import jakarta.transaction.Transactional;

@Repository
public interface sComment_LikeRepository extends JpaRepository<sComment_Like, Integer> { // sComment_Like로 변경

	Optional<sComment_Like> findByScommentAndMember(sComment scomment, Member member); // findByComment -> findByScomment

	long countByScomment(sComment scomment);
	
	// sComment의 scno를 기준으로 좋아요 삭제
    @Modifying
    @Transactional
    void deleteByScommentScno(int scno);
    
    List<sComment_Like> findByMember(Member member);
	
    @Modifying
    @Transactional
    @Query("DELETE FROM sComment_Like cl WHERE cl.scomment.board.bno = :bno")
	void deleteByBoardBno(@Param("bno") int bno);
}