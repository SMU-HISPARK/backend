package com.java.service;

import org.springframework.stereotype.Service;

import com.java.dto.Member;
import com.java.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findById(int id) {
        return memberRepository.findById(id).orElse(null);
    }
}
