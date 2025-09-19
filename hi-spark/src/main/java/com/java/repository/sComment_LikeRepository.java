package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.dto.sComment; // sComment로 변경
import com.java.dto.sComment_Like; // sComment_Like로 변경
import com.java.entity.Member;

@Repository
public interface sComment_LikeRepository extends JpaRepository<sComment_Like, Integer> { // sComment_Like로 변경

	Optional<sComment_Like> findByScommentAndMember(sComment scomment, Member member); // findByComment -> findByScomment

	long countByScomment(sComment scomment);
}