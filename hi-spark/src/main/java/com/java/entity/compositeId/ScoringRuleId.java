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
public class ScoringRuleId implements Serializable {

	private Integer option_id;
	private Integer club_id;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null | getClass() != o.getClass()) return false;
		ScoringRuleId sRuleId = (ScoringRuleId) o;
		return Objects.equals(option_id, sRuleId.option_id)
				&& Objects.equals(club_id, sRuleId.club_id);
	}

    @Override
    public int hashCode() {
    	return Objects.hash(option_id, club_id);
    }	
	
}
