package com.java.service;


import java.util.Optional;

import com.java.dto.MemberDto;
import com.java.entity.Member;

public interface MemberService {


	MemberDto findByLoginIdAndPassword(String id, String pw);

	void save(Member member);

	Member findById(int memberId);

	Optional<Member> findByLoginId(String loginId);
	
	
}
