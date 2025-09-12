package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	Member findByLoginId(String id);

	
}
