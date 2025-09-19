package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.Poll_Item;

import jakarta.transaction.Transactional;

@Repository
public interface Poll_ItemRepository extends JpaRepository<Poll_Item, Integer> {
    
    // 특정 투표의 모든 항목을 가져오는 메서드
    @Query("SELECT pi FROM Poll_Item pi WHERE pi.poll.poll_no = :pollNo")
    List<Poll_Item> findByPollNo(@Param("pollNo") int pollNo);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Poll_Item pi WHERE pi.poll.poll_no = :pollNo")
    void deleteByPoll_PollNo(@Param("pollNo") int pollNo);
}