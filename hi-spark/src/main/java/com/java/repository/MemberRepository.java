package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.java.dto.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    
    // loginId로 회원을 찾는 메소드 추가
    Member findByLoginId(String loginId);

	Optional<Member> findByLoginIdAndPassword(String loginId, String password);
}