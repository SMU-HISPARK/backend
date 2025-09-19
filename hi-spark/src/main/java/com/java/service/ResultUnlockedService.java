package com.java.service;

import java.util.List;

import com.java.dto.ClubDto;
import com.java.entity.userData.GameRun;
import com.java.entity.Member;
import com.java.entity.sourceData.GameResultClub;

public interface ResultUnlockedService {

	List<ClubDto> getClubsByMember(Member member);

	List<GameResultClub> getAllClubs();

	List<GameRun> getMyClubs(String loginId);

}
