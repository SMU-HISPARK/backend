package com.java.Service;

import com.java.dto.Member;
import java.util.Optional;

public interface MemberService {
    // 회원 가입
    Member registerMember(Member member);
    
    // 회원 조회 (로그인 시 사용)
    Optional<Member> getMemberById(int memberId);

    // 회원 조회 (로그인 시 사용 - 아이디(loginId)와 비밀번호로 찾기)
    Optional<Member> getMemberByLoginIdAndPassword(String loginId, String password);

    // 회원 정보 수정
    Optional<Member> updateMember(Member member);

    // 회원 탈퇴
    void deleteMember(int memberId);
}