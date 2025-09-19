package com.java.dto;

import java.util.List;

import com.java.entity.sourceData.GameOptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatDto {

	// 총 게임 참여 수
	private Long runs;
	// 참여 결과 비율
	List<Double> resultRateList;
	/* 도서부, 밴드부, 선도부, 운동부, 제과제빵부 순	*/
	
	// 참여자 중 회원 수
	private Long memberCount;
	// 회원 중 여러 결과를 본 비율
	List<Double> multiClubMemberRateList;
	/* 1,2,3,4,5 순 */
	
	// 가장 높은 비율로 선택된 선택지 (응답 10회 이상 중)
	private GameOptions mostSelectedOption;
	
	
	// 이스터에그 오픈 비율
	// private Double easterEggRate;
	
	
}
