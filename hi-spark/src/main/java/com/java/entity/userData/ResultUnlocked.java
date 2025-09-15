package com.java.entity.userData;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.java.entity.Member;
import com.java.entity.compositeId.UnlockedId;
import com.java.entity.sourceData.GameResultClub;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
	@JoinColumn(name="member_id", nullable = false)
	private Member member;
	
	@ManyToOne
	@MapsId("club_id")
	@JoinColumn(name="club_id", nullable = false)
	private GameResultClub resultClub;
	
	
	@OneToOne
	@JoinColumn(name="finished_at", nullable = false)
	private GameRun gameRun;
	
	
	/// 필드
	/*
	@Column(nullable = false)
	private String memberId;
	
	@Column(nullable = false)
	private Integer club_id;
	*/
}
