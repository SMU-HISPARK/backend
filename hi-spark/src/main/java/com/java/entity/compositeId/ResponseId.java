package com.java.entity.compositeId;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseId implements Serializable {

	private Integer run_id;
	private Integer question_id;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null | getClass() != o.getClass()) return false;
		ResponseId responseId = (ResponseId) o;
		return Objects.equals(run_id, responseId.run_id)
				&& Objects.equals(question_id, responseId.question_id);
	}

    @Override
    public int hashCode() {
    	return Objects.hash(run_id, question_id);
    }	
	
}
