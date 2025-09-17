package com.java.repository;

import com.java.entity.Board;
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
public interface BoardRepository extends JpaRepository<Board, Integer> {
	
    List<Board> findAllByMemberOrderByBdateDesc(Member member);
    
     
    @Query("SELECT TO_CHAR(b.bdate, 'YYYY-MM-DD'), COUNT(b) " +
    	       "FROM Board b " +
    	       "WHERE b.bdate BETWEEN :start AND :end " +
    	       "GROUP BY TO_CHAR(b.bdate, 'YYYY-MM-DD') " +
    	       "ORDER BY TO_CHAR(b.bdate, 'YYYY-MM-DD')")
    	List<Object[]> countPostsByDateBetween(@Param("start") LocalDateTime start,
    	                                      @Param("end") LocalDateTime end);
    	
    
}