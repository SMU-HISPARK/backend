package com.java.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.java.dto.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comments {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cno;
	
	@CreationTimestamp
	private Timestamp cdate;
	
	@UpdateTimestamp
	@Column(name = "up_cdate")
	private Timestamp upCdate;
	
	@ManyToOne
	@JoinColumn(name = "bno")
	private Board board;
	
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;
	
	@Lob
    @Column
    private String ccontent;
}
