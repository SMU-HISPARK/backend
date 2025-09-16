package com.java.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{

	Optional<Member> findByLoginIdAndPassword(String loginId, String password);

	
}
