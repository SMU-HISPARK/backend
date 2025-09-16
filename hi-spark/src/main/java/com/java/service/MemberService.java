package com.java.service;


import com.java.dto.Member;

public interface MemberService {


	Member findByLoginIdAndPassword(String id, String pw);

	Member findByLoginId(String loginId);


}
