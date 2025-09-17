package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.sComment; // sComment로 변경

@Repository
public interface sCommentRepository extends JpaRepository<sComment, Integer> { // sComment로 변경
    // 필요에 따라 추가적인 쿼리 메서드 작성 가능

	@Query("SELECT c FROM sComment c WHERE c.board.bno = :bno ORDER BY c.scno ASC")
	List<sComment> findByBoard_BnoOrderByScno(@Param("bno") int bno);

	// 댓글과 작성자 정보를 함께 가져오는 새로운 쿼리 메서드 추가
    @Query("SELECT c FROM sComment c LEFT JOIN FETCH c.member WHERE c.board.bno = :bno ORDER BY c.scno ASC")
    List<sComment> findByBoard_BnoWithMember(@Param("bno") int bno);
	
	long countByBoard_Bno(int bno);
}