package com.java.dto;


import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.java.dto.Board;
import com.java.dto.Member;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cno;
	
	@ManyToOne
    @JoinColumn(name = "bno")
    private Board board;
	
	@Column(columnDefinition = "TEXT")
	private String ccontent;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member; //FK
	
	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp cdate;
	
	@Column(nullable = true)
	@UpdateTimestamp
	private Timestamp up_cdate;
}
