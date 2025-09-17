package com.java.entity.sourceData;

import com.java.entity.compositeId.ScoringRuleId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ScoringRules {

	@EmbeddedId
	private ScoringRuleId scoringRuleId;
	
	@ManyToOne
	@MapsId("optionId")	// ScoringRuleId 클래스
	@JoinColumn(name="optionId")
	private GameOptions option;
	
	@ManyToOne
	@MapsId("clubId")		// ScoringRuledId 클래스
	@JoinColumn(name="clubId")
	private GameResultClub club;
	
	/// 필드
	
	/*
	private Integer option_id;
	
	private Integer club_id;
	*/
	
	private Integer scoringDelta;
	
}
