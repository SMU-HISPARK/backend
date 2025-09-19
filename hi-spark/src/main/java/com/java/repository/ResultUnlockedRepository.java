package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.dto.ClubDto;
import com.java.entity.Member;
import com.java.entity.userData.ResultUnlocked;
import com.java.entity.compositeId.ResponseId;
import com.java.entity.compositeId.UnlockedId;

@Repository
public interface ResultUnlockedRepository extends JpaRepository<ResultUnlocked, UnlockedId> {
    
	List<ResultUnlocked> findByMember(Member member);
}
