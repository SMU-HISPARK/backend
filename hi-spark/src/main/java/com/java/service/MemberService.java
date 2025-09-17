package com.java.service;

import com.java.dto.MemberDto;
import com.java.entity.Member;

public interface MemberService {

	void save(Member member);

	MemberDto findByLoginIdAndPassword(String loginId, String password);
	// cart
	Member findById(Integer memberId);
	//login
	Member findById(String loginId);

}