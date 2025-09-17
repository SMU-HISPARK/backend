package com.java.entity.userData;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GameSession {

	@Id
	@Column(length = 36)
	private String sessionId;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	private LocalDateTime lastSeen;
	
	@Column(nullable = false)
	private LocalDateTime expiresAt;
	
	@PrePersist
	public void prePersist() {
		if (expiresAt == null) {
			expiresAt = LocalDateTime.now().plusDays(30);
		}
	}
	
}
