package com.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dto.MemberDto;
import com.java.entity.Member;
import com.java.respository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired MemberRepository mRep;
	
	@Override
	public void save(Member member) {
		
		mRep.save(member);
		
	}

	@Override
	public MemberDto findByLoginIdAndPassword(String loginId, String password) {
		
		Member member = mRep.findByLoginIdAndPassword(loginId, password).orElseGet(null);
		if(member == null) return null;
		MemberDto memfind = new MemberDto(member.getLoginId(), member.getNickname());
		
		return memfind;
	}

	
}
