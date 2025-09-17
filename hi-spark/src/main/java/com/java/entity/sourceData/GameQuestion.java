package com.java.entity.sourceData;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
		name = "questionSeqGen",
		sequenceName = "questionSeq",
		initialValue = 1,
		allocationSize = 1)
public class GameQuestion {

	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "questionSeqGen"
			)
	private Integer questionId;
	
	@Lob
	@Column(nullable = false)
	private String text;
	
	@Column(length = 50)
	private String tag;
	
	private Integer day;
	private Integer time;
	
	@Column(nullable = false)
	private String image;
	
	
	// 읽기 전용 필드(컬럼 X)
	
	@OneToMany(
			mappedBy = "question",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, 
	        orphanRemoval = true)
	@OrderBy("optionNo ASC")
	@ToString.Exclude
	@JsonIgnoreProperties({"question"})
	private List<GameOptions> options;

}
