package com.java.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dto.MemberDto;
import com.java.entity.Member;
import com.java.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired MemberRepository memberRepository;
	
	@Override
	public void save(Member member) {
		memberRepository.save(member);
	}


	// 수정
	@Override
	public MemberDto findByLoginIdAndPassword(String loginId, String password) {
	    Optional<Member> memberOpt = memberRepository.findByLoginIdAndPassword(loginId, password);

	    return memberOpt.map(MemberDto::fromEntity).orElse(null);
	}

	@Override
	public Member findById(int memberId) {
		Member member = memberRepository.findById(memberId).orElseGet(
				()-> {return(new Member());}
				);
		
		return member;
	}
	
	@Override
	public Optional<Member> findByLoginId(String loginId) {
		Optional<Member> member = memberRepository.findByLoginId(loginId);
		return member;
	}
	

}