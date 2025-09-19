package com.java.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.sComment; // sComment로 변경
import com.java.entity.Member;

@Repository
public interface sCommentRepository extends JpaRepository<sComment, Integer> { // sComment로 변경
    // 필요에 따라 추가적인 쿼리 메서드 작성 가능

	@Query("SELECT c FROM sComment c WHERE c.board.bno = :bno ORDER BY c.scno ASC")
	List<sComment> findByBoard_BnoOrderByScno(@Param("bno") int bno);

	// 댓글과 작성자 정보를 함께 가져오는 새로운 쿼리 메서드 추가
    @Query("SELECT c FROM sComment c LEFT JOIN FETCH c.member WHERE c.board.bno = :bno ORDER BY c.scno ASC")
    List<sComment> findByBoard_BnoWithMember(@Param("bno") int bno);
	
	long countByBoard_Bno(int bno);
	
	List<sComment> findByMemberOrderByScdateDesc(Member member); // 내 댓글 최신순
	   
	   @Query("SELECT TO_CHAR(c.scdate, 'YYYY-MM-DD'), COUNT(c) " +
	             "FROM sComment c " +
	             "WHERE c.scdate BETWEEN :startDate AND :endDate " +
	             "GROUP BY TO_CHAR(c.scdate, 'YYYY-MM-DD') " +
	             "ORDER BY TO_CHAR(c.scdate, 'YYYY-MM-DD')")
	      List<Object[]> countCommentsByDate(@Param("startDate") LocalDateTime start,
	                                        @Param("endDate") LocalDateTime end);

	   

	    @Query(value = "SELECT TRUNC(scdate) AS postDate, COUNT(scno) AS commentCount " +
	                   "FROM sComment " +
	                   "WHERE scdate BETWEEN :start AND :end " +
	                   "GROUP BY TRUNC(scdate) " +
	                   "ORDER BY TRUNC(scdate)", nativeQuery = true)
	    List<Object[]> countCommentsByDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);


}