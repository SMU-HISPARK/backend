package com.java.entity.userData;

import com.java.entity.compositeId.ResponseId;
import com.java.entity.sourceData.GameOptions;

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
	private ResponseId responseId;
	
	@ManyToOne
	@MapsId("runId")
	@JoinColumn(name = "runId", nullable = false)
	private GameRun gameRun;
	
	@ManyToOne
	@MapsId("optionId")
	@JoinColumn(name = "optionId", nullable = false)
	private GameOptions options;
	
}
