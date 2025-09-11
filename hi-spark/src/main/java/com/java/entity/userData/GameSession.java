package com.java.entity.userData;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	private String session_id;
	
	@CreationTimestamp
	private Timestamp created_at;
	
	private Timestamp last_seen;
	
	private Timestamp expires_at;
	
}
