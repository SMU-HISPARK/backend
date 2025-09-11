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
public class UnlockedId implements Serializable {

	private String id;
	private Integer club_id;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null | getClass() != o.getClass()) return false;
		UnlockedId unlockedId = (UnlockedId) o;
		return Objects.equals(id, unlockedId.id)
				&& Objects.equals(club_id, unlockedId.club_id);
	}

    @Override
    public int hashCode() {
    	return Objects.hash(id, club_id);
    }	
	
}
