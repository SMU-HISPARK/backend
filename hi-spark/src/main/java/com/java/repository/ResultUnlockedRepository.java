package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.dto.ClubDto;
import com.java.entity.Member;
import com.java.entity.ResultUnlocked;
import com.java.entity.compositeId.ResponseId;

@Repository
public interface ResultUnlockedRepository extends JpaRepository<ResultUnlocked, ResponseId> {
    
	List<ResultUnlocked> findByMember(Member member);
}
