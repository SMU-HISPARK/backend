package com.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dto.Member;
import com.java.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired MemberRepository memberRepository;
	

	@Override
	public Member findByLoginIdAndPassword(String id, String pw) {

		Member member = memberRepository.findByLoginIdAndPassword(id, pw);
			
		return member;

	}


	@Override
	public Member findByLoginId(String loginId) {
		Member member = memberRepository.findByLoginId(loginId);
		return member;
	}


}
