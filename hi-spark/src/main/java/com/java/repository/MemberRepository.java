package com.java.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.dto.Member;


@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
	

	Member findByLoginIdAndPassword(String id, String pw);

	Member findByLoginId(String loginId);

}
