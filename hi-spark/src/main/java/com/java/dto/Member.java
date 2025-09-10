package com.java.dto;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int member_id;
	
	@Column(nullable=false,length=100)
	private String username;
	
	@Column(nullable=false,length=100)
	private String name;
	
	@Column(nullable=false,length=100)
	private String nickname;
	
	@Column(nullable=false,length=100)
	private String password;
	
	@Column(nullable=false,length=100)
	private String phone;
	
	@Column(nullable=false,length=100)
	private String email;
	
	@CreationTimestamp
	private Timestamp created_at;
	
}
