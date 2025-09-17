package com.java.service;

import java.util.List;

import com.java.dto.ClubDto;
import com.java.entity.GameResultClub;
import com.java.entity.GameRun;
import com.java.entity.Member;

public interface ResultUnlockedService {

	List<ClubDto> getClubsByMember(Member member);

	List<GameResultClub> getAllClubs();

	List<GameRun> getMyClubs(String loginId);

}
