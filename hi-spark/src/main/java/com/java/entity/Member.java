//package com.java.entity;
//
//import java.sql.Timestamp;
//
//import org.hibernate.annotations.CreationTimestamp;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.SequenceGenerator;
//import jakarta.validation.constraints.Pattern;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Member {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="member_id")
//	private int memberId;
//	
//	@Pattern(regexp = "[a-z0-9_]{4,16}$",
//			message = "영문 소문자, 숫자, 언더바_만 허용됩니다.")
//	@Column(name="login_id", length = 16, nullable = false, unique = true)
//	private String loginId;
//	
//	@Pattern(regexp = "^[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?`~]{8,20}$")
//	@Column(length = 20, nullable = false)
//	private String password;
//	
//	@Column(length = 50, nullable = false)
//	private String name;
//	
//	@Column(length = 30, unique = true)
//	private String nickname;
//	
//	@Column(length = 20, unique = true)
//	private String phone;
//	
//	@Column(length = 100, unique = true)
//	private String email;
//	
//	@Column(nullable=false)
//	private int point;
//	
//	@CreationTimestamp
//	@Column(name="created_at")
//	private Timestamp createdAt;
//	
//}