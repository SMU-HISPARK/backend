package com.java.entity.sourceData;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
		name = "option_seq_gen",
		sequenceName = "option_seq",
		initialValue = 1,
		allocationSize = 1)
@Table(
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"question_id", "number"})
		})
public class GameOptions {

	
	// FK 설정
	@ManyToOne
	@JoinColumn(name = "question_id")
	private GameQuestion question;
	
	/// 필드
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer option_id;
	
	@Column(nullable = false)
	private Integer question_id;
	
	@Column(nullable = false)
	private Integer number;
	
	@Lob
	@Column(nullable = false)
	private String text;
	
	
	// 읽기 전용 필드(컬럼 X)
	
	@OneToMany(
			mappedBy = "option_id",
			fetch = FetchType.LAZY, 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true)
	private List<ScoringRules> Scoring;
}
