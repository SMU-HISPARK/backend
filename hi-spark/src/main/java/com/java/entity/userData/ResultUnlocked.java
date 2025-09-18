package com.java.entity.userData;

import java.sql.Timestamp;

import com.java.entity.Member;
import com.java.entity.compositeId.UnlockedId;
import com.java.entity.sourceData.GameResultClub;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultUnlocked {

	// 복합 키 아이디
	@EmbeddedId
	UnlockedId unlockedId;
	
	@ManyToOne
	@MapsId("memberId")
	@JoinColumn(name="member_id", nullable = false, updatable = false)
	private Member member;
	
	@ManyToOne
	@MapsId("clubId")
	@JoinColumn(name="clubId", nullable = false, updatable = false)
	private GameResultClub resultClub;
	
	@ManyToOne
	@JoinColumn(name="runId", nullable = false, updatable = false)
	private GameRun gameRun;
	
	@Column(nullable = false, updatable = false)
	private Timestamp finishedAt;
	
	@PrePersist
	void syncFinishedAt() {
		this.finishedAt = gameRun.getFinishedAt();
	}
	
	/// 필드
	/*
	@Column(nullable = false)
	private String memberId;
	
	@Column(nullable = false)
	private Integer club_id;
	*/
}
