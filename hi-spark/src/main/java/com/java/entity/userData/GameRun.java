package com.java.entity.userData;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SequenceGenerator(
		name = "run_seq_gen",
		sequenceName = "run_seq",
		initialValue = 1,
		allocationSize = 1)
public class GameRun {
	
	// FK 지정
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = true)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "session_id", nullable = true)
	private GameSession gameSession;
	
	@ManyToOne
	@JoinColumn(name = "club_id", nullable = false)
	private GameResultClub resultClub;
	
	
	// 컬럼
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "run_seq_gen")
	private Long run_id;
	
	@Column(length = 30, nullable = false, unique = false)
	private String user_name;
	
	/*
	@Column(nullable = true)
	private String memberId;
	
	@Column(nullable = true)
	private String session_id;
	
	@Column(nullable = false)
	private Integer club_id;
	*/
	
	@CreationTimestamp
	private Timestamp finished_at;
	
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
	private List<QuestionResponse> responses;
	
	
}
