package com.java.entity.sourceData;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.PartitionKey;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResultClub {

	@Id
	private Integer club_id;
	
	@Column(length = 100, nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String result_image;
	
	@ColumnDefault("0")
	private Integer result_count;
}
