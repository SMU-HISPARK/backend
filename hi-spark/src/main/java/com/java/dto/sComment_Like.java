package com.java.dto;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sComment_Like")
public class sComment_Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scl_no;
	
	@ManyToOne
    @JoinColumn(name = "scno")
	private sComment scomment; // sComment로 변경
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@CreationTimestamp
	private Timestamp scl_date;
}