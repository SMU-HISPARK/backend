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
public class UnlockedId implements Serializable {

	private Integer memberId;
	private Integer clubId;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UnlockedId)) return false;
		UnlockedId unlockedId = (UnlockedId) o;
		return Objects.equals(memberId, unlockedId.memberId)
				&& Objects.equals(clubId, unlockedId.clubId);
	}

    @Override
    public int hashCode() {
    	return Objects.hash(memberId, clubId);
    }	
	
}
