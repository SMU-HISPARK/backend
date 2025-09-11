package com.java.entity.userData;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.java.entity.Member;
import com.java.entity.sourceData.GameOptions;
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
import jakarta.persistence.OrderBy;
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
	@JoinColumn(name = "id")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "session_id")
	private GameSession gameSession;
	
	@ManyToOne
	@JoinColumn(name = "club_id")
	private GameResultClub resultClub;
	
	
	// 컬럼
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long run_id;
	
	@Column(length = 30, nullable = false, unique = false)
	private String user_name;
	
	@Column(nullable = true)
	private String id;
	
	@Column(nullable = true)
	private String session_id;
	
	@Column(nullable = false)
	private Integer club_id;
	
	@CreationTimestamp
	private Timestamp finished_at;
	
	@ColumnDefault("0")
	private Integer score1;
	
	@ColumnDefault("0")
	private Integer score2;
	
	@ColumnDefault("0")
	private Integer score3;
	
	@ColumnDefault("0")
	private Integer score4;
	
	@ColumnDefault("0")
	private Integer score5;
	
	// 읽기 전용 필드 (컬럼 X)
	
	@OneToMany(
			mappedBy = "run_id",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, 
	        orphanRemoval = true)
	private List<QuestionResponse> responses;
	
	
}
