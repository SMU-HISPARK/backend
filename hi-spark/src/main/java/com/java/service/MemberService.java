package com.java.service;

import com.java.dto.MemberDto;
import com.java.entity.Member;

public interface MemberService {

	void save(Member member);

	MemberDto findByUsernameAndPassword(String username, String password);

}
