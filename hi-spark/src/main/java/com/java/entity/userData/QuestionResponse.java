package com.java.entity.userData;

import com.java.entity.compositeId.ResponseId;
import com.java.entity.sourceData.GameOptions;
import com.java.entity.sourceData.GameQuestion;

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
public class QuestionResponse {

	@EmbeddedId
	private ResponseId id;
	
	@ManyToOne
	@MapsId("run_id")
	@JoinColumn(name = "run_id")
	private GameRun gameRun;
	
	@ManyToOne
	@MapsId("question_id")
	@JoinColumn(name = "question_id")
	private GameQuestion question;
	
	@ManyToOne
	@JoinColumn(name = "option_id")
	private GameOptions options;
	
	
	// 필드
	
	@Column(nullable = false)
	private Long run_id;
	
	@Column(nullable = false)
	private Integer quetion_id;
	
	@Column(nullable = false)
	private Integer option_id;
}
