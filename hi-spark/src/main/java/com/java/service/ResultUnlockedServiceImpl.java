package com.java.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dto.ClubDto;
import com.java.entity.GameResultClub;
import com.java.entity.GameRun;
import com.java.entity.Member;
import com.java.entity.ResultUnlocked;
import com.java.repository.GameResultClubRepository;
import com.java.repository.GameRunRepository;
import com.java.repository.ResultUnlockedRepository;

@Service
public class ResultUnlockedServiceImpl implements ResultUnlockedService {

    @Autowired
    private ResultUnlockedRepository resultUnlockedRepository;
    @Autowired private GameResultClubRepository gameResultClubRepository;
    @Autowired private GameRunRepository gameRunRepository;
    

    @Override
    public List<ClubDto> getClubsByMember(Member member) {
        // 엔티티로 조회
        List<ResultUnlocked> results = resultUnlockedRepository.findByMember(member);
        
        // DTO 변환 시 이미지 URL 추가
        return results.stream()
            .map(r -> {
                ClubDto dto = new ClubDto();
                dto.setClubId(r.getClub().getClubId());
                dto.setName(r.getClub().getName());
                return dto;
            })
            .collect(Collectors.toList());
    }

    

    // 모든 동아리
    public List<GameResultClub> getAllClubs() {
        return gameResultClubRepository.findAll();
    }

    // 로그인한 사용자가 가입한 동아리
    public List<GameRun> getMyClubs(String loginId) {
        return gameRunRepository.findByMember_LoginId(loginId);
    }

}
