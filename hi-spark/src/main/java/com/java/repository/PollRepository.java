package com.java.repository;

import java.sql.Timestamp; 
import java.util.Optional; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {
    
    // 투표 번호(poll_no) 내림차순으로 모든 투표를 가져오는 쿼리
    @Query("SELECT p FROM Poll p ORDER BY p.poll_no DESC")
    Page<Poll> findAllByOrderByPoll_noDesc(Pageable pageable);

    // 진행 중인 투표(종료일이 현재보다 미래인 투표)만 가져오는 쿼리
    @Query("SELECT p FROM Poll p WHERE p.poll_end_date > :currentTime ORDER BY p.poll_no DESC")
    Page<Poll> findAllActivePolls(@Param("currentTime") Timestamp currentTime, Pageable pageable);
    
    // 제목으로 검색하는 쿼리
    @Query("SELECT p FROM Poll p WHERE p.poll_title LIKE %:keyword% ORDER BY p.poll_no DESC")
    Page<Poll> searchByTitle(@Param("keyword") String keyword, Pageable pageable);

    // 내용으로 검색하는 쿼리
    @Query("SELECT p FROM Poll p WHERE p.poll_content LIKE %:keyword% ORDER BY p.poll_no DESC")
    Page<Poll> searchByContent(@Param("keyword") String keyword, Pageable pageable);
    
    // 이전 글을 찾는 메서드 (JPQL 사용)
    @Query("SELECT p FROM Poll p WHERE p.poll_no < :pollNo ORDER BY p.poll_no DESC LIMIT 1")
    Optional<Poll> findPreviousPoll(@Param("pollNo") int pollNo);
    
    // 다음 글을 찾는 메서드 (JPQL 사용)
    @Query("SELECT p FROM Poll p WHERE p.poll_no > :pollNo ORDER BY p.poll_no ASC LIMIT 1")
    Optional<Poll> findNextPoll(@Param("pollNo") int pollNo);
}