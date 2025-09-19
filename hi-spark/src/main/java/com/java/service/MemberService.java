package com.java.service;

import java.util.Optional;

import com.java.dto.MemberDto;
import com.java.entity.Member;

public interface MemberService {

	void save(Member member);

	MemberDto findByLoginIdAndPassword(String loginId, String password);
	// cart
	Member findById(Integer memberId);
	//login
	Optional<Member> findByLoginId(String loginId);

	Member findById(String loginId);
}