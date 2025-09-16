package com.java.entity;


import com.java.dto.Member;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ResultUnlocked {

	@EmbeddedId
    private com.java.entity.compositeId.ResponseId id;
	
	
	@ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
	
	@ManyToOne
	@JoinColumn(name="club_id")
	private GameResultClub clubId;
	
	@ManyToOne
	@JoinColumn(name="result_count")
	private GameResultClub resultCount;
	
}
