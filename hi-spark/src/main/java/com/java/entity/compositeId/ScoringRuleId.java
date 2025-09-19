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
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoringRuleId implements Serializable {

	private Integer optionId;
	private Integer clubId;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ScoringRuleId)) return false;
		ScoringRuleId sRuleId = (ScoringRuleId) o;
		return Objects.equals(optionId, sRuleId.optionId)
				&& Objects.equals(clubId, sRuleId.clubId);
	}

    @Override
    public int hashCode() {
    	return Objects.hash(optionId, clubId);
    }	
	
}
