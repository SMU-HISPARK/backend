package com.java.entity.compositeId;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ResponseId implements Serializable {

	private Long runId;
	private Integer optionId;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ResponseId)) return false;
		ResponseId responseId = (ResponseId) o;
		return Objects.equals(runId, responseId.runId)
				&& Objects.equals(optionId, responseId.optionId);
	}

    @Override
    public int hashCode() {
    	return Objects.hash(runId, optionId);
    }	
	
}
