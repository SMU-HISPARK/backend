package com.java.entity.sourceData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
		name = "optionSeqGen",
		sequenceName = "optionSeq",
		initialValue = 1,
		allocationSize = 1)
@Table(
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"questionId", "optionNo"})
		})
public class GameOptions {

	
	// FK 설정
	@ManyToOne
	@JoinColumn(name = "questionId", nullable = false)
	private GameQuestion question;
	
	/// 필드
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "optionSeqGen"
			)
	private Integer optionId;
	
	/*
	@Column(nullable = false)
	private Integer question_id;
	*/
	
	@Column(nullable = false)
	private Integer optionNo;
	
	@Lob
	@Column(nullable = false)
	private String text;
	
	@Column(length = 50, nullable = true)
	private String tag;
	
	// 읽기 전용 필드(컬럼 X)
	
	/*
	@OneToMany(
			mappedBy = "option",
			fetch = FetchType.LAZY, 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true)
	@ToString.Exclude
	@JsonIgnoreProperties({"option"})
	private List<ScoringRules> Scoring;
	*/
	
}
