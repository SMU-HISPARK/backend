package com.java.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MemberDto {

	private int memberId;
	private String loginId;
    private String nickname;
    private String name;
    private String email;
    private Timestamp createdAt;
    
    // 관리자
    private String role;

    // phone
    private String phone1;
    private String phone2;
    private String phone3;

    private String password; 
    private int point;
    
    
    public MemberDto(int memberId, String loginId, String nickname) {
    	this.memberId = memberId;
    	this.loginId = loginId;
    	this.nickname = nickname;
    	}
    
    
    // Member 엔티티를 DTO로 변환하는 정적 메서드
    public static MemberDto fromEntity(com.java.entity.Member member) {
        if (member == null) return null;
        
        String phone = member.getPhone() != null ? member.getPhone() : "";
        String p1 = "", p2 = "", p3 = "";
        if (phone.length() >= 3) p1 = phone.substring(0, 3);
        if (phone.length() >= 8) p2 = phone.substring(4, 8);
        if (phone.length() >= 11) p3 = phone.substring(9);
        
        // 관리자 권한 설정 - ROLE_ 접두사 포함
        String role = "ROLE_USER"; // 기본 일반 사용자
        if ("hi_spark".equals(member.getLoginId())) {
            role = "ROLE_ADMIN"; // 관리자 권한
            System.out.println("관리자 계정 확인: " + member.getLoginId() + " -> " + role);
        }
        
        return MemberDto.builder()
                .memberId(member.getMemberId()) // memberId 추가
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .phone1(p1)
                .phone2(p2)
                .phone3(p3)
                .name(member.getName()) 
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .password(member.getPassword())
                .point(member.getPoint())
                .role(role)
                .build();
    }
	
}