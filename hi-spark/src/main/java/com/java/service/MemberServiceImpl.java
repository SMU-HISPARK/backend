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

	@Override
	public MemberDto findByLoginIdAndPassword(String loginId, String password) {
		
		Member member = memberRepository.findByLoginIdAndPassword(loginId, password).orElse(null);
		if(member == null) return null;
		MemberDto memfind = new MemberDto(member.getMemberId(),member.getLoginId(), member.getNickname());
		
		return memfind;
	}
	
	

	@Override
	public Member findById(String loginId) {
	    return memberRepository.findByLoginId(loginId).orElse(null);
	}

	@Override
	public Member findById(Integer memberId) {
		Member member = memberRepository.findById(memberId).get();
		return member;
	}

	@Override
	public Optional<Member> findByLoginId(String loginId) {
		Optional<Member> member = memberRepository.findByLoginId(loginId);
		return member;
	}
	

	@Override
	public boolean existsByLoginId(String loginId) {
		return memberRepository.existsByLoginId(loginId);
	}

	@Override
	public boolean existsByEmail(String email) {
		return memberRepository.existsByEmail(email);
	}

	
	
	
}