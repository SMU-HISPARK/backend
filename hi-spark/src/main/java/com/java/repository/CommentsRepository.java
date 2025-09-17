package com.java.repository;

import com.java.entity.Comments;
import com.java.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
	
	List<Comments> findByMemberOrderByCdateDesc(Member member); // 내 댓글 최신순
	
	@Query("SELECT TO_CHAR(c.cdate, 'YYYY-MM-DD'), COUNT(c) " +
		       "FROM Comments c " +
		       "WHERE c.cdate BETWEEN :startDate AND :endDate " +
		       "GROUP BY TO_CHAR(c.cdate, 'YYYY-MM-DD') " +
		       "ORDER BY TO_CHAR(c.cdate, 'YYYY-MM-DD')")
		List<Object[]> countCommentsByDate(@Param("startDate") LocalDateTime start,
		                                  @Param("endDate") LocalDateTime end);
}


