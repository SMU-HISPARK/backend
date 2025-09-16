package com.java.repository;

import java.util.Optional; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.Vote_Log;

import jakarta.transaction.Transactional; // import 추가

@Repository
public interface Vote_LogRepository extends JpaRepository<Vote_Log, Integer> {
    
    // Poll 번호로 투표 수를 세는 메서드
    @Query("SELECT COUNT(v) FROM Vote_Log v WHERE v.poll.poll_no = :pollNo")
    long countByPollNo(@Param("pollNo") int pollNo);
    
    // poll_no와 loginId를 기준으로 투표 기록이 있는지 확인하는 메소드
    @Query("SELECT v FROM Vote_Log v WHERE v.poll.poll_no = :pollNo AND v.member.loginId = :loginId")
    Optional<Vote_Log> findByPoll_PollNoAndMember_LoginId(@Param("pollNo") int pollNo, @Param("loginId") String loginId);

    // 투표 항목 번호로 투표 수를 세는 메서드 (수정)
    @Query("SELECT COUNT(v) FROM Vote_Log v WHERE v.pollItem.item_no = :itemNo")
    long countByPollItemNo(@Param("itemNo") int itemNo);
    
    // 투표 번호에 해당하는 투표 기록을 모두 삭제하는 메서드
    @Modifying
    @Transactional
    @Query("DELETE FROM Vote_Log v WHERE v.poll.poll_no = :pollNo")
    void deleteByPoll_PollNo(@Param("pollNo") int pollNo);
}