package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.java.dto.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
