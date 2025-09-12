package com.java.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.entity.Member;
import com.java.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired MemberRepository memberRepository;
	
	@Override
	public Member findById(int memberId) {
		Member member = memberRepository.findById(memberId).orElseGet(
				()-> {return(new Member());}
				);
		
		return member;
	}

}
