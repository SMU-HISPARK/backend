package com.java.Service;

import com.java.dto.Member;
import com.java.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional
    public Member registerMember(Member member) {
        // 회원 가입 로직을 여기에 구현합니다.
        // 예를 들어, 중복 아이디 확인 등의 로직을 추가할 수 있습니다.
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> getMemberById(int memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public Optional<Member> getMemberByLoginIdAndPassword(String loginId, String password) {
        // 실제 비밀번호는 암호화해서 비교해야 합니다.
        // 여기서는 예시로 필드를 직접 사용합니다.
        return memberRepository.findByLoginIdAndPassword(loginId, password);
    }

    @Override
    @Transactional
    public Optional<Member> updateMember(Member member) {
        if (!memberRepository.existsById(member.getMemberId())) {
            return Optional.empty();
        }
        return Optional.of(memberRepository.save(member));
    }

    @Override
    @Transactional
    public void deleteMember(int memberId) {
        memberRepository.deleteById(memberId);
    }
}