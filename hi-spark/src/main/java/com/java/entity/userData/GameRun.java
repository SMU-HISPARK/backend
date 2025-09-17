package com.java.entity.userData;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.java.entity.Member;
import com.java.entity.sourceData.GameResultClub;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SequenceGenerator(
		name = "runSeqGen",
		sequenceName = "runSeq",
		initialValue = 1,
		allocationSize = 1)
public class GameRun {
	
	// FK 지정
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = true)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "sessionId", nullable = true)
	private GameSession gameSession;
	
	@ManyToOne
	@JoinColumn(name = "clubId", nullable = false)
	private GameResultClub resultClub;
	
	
	// 컬럼
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "runSeqGen")
	private Long runId;
	
	@Column(length = 30, nullable = false, unique = false)
	private String userName;
	
	/*
	@Column(nullable = true)
	private String memberId;
	
	@Column(nullable = true)
	private String session_id;
	
	@Column(nullable = false)
	private Integer club_id;
	*/
	
	@CreationTimestamp
	private Timestamp finishedAt;
	
	@ColumnDefault("0")
	@Column(nullable = false)
	@Builder.Default
	private Integer score1 = 0;
	
	@ColumnDefault("0")
	@Column(nullable = false)
	@Builder.Default
	private Integer score2 = 0;
	
	@ColumnDefault("0")
	@Column(nullable = false)
	@Builder.Default
	private Integer score3 = 0;
	
	@ColumnDefault("0")
	@Column(nullable = false)
	@Builder.Default
	private Integer score4 = 0;
	
	@ColumnDefault("0")
	@Column(nullable = false)
	@Builder.Default
	private Integer score5 = 0;
	
	// 읽기 전용 필드 (컬럼 X)
	
	@OneToMany(
			mappedBy = "gameRun",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, 
	        orphanRemoval = true)
	@ToString.Exclude
	@JsonIgnoreProperties({"gameRun"})
	private List<QuestionResponse> responses;
	
	
}
