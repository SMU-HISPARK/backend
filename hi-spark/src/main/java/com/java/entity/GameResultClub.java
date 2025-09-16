package com.java.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class GameResultClub {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="club_id")
	private int clubId;
	
	@Column(nullable = false,unique = true)
	private String name;
	
	@Column(name = "result_image",nullable = false,unique = true)
	private String resultImage;
	
	@Column(name = "result_count", unique = true, nullable = false)
	private String resultCount;
	
}
