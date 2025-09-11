package com.java.entity.userData;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.java.entity.Member;
import com.java.entity.compositeId.UnlockedId;
import com.java.entity.sourceData.GameResultClub;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
	@MapsId("id")
	@JoinColumn(name="id")
	private Member member;
	
	@ManyToOne
	@MapsId("club_id")
	@JoinColumn(name="club_id")
	private GameResultClub resultClub;
	
	
	/// 필드
	
	@Column(nullable = false)
	private String id;
	
	@Column(nullable = false)
	private Integer club_id;
	
	@CreationTimestamp
	private Timestamp finished_at;
}
