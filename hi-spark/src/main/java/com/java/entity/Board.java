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
public class Board {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bno;
	
	@Column(nullable = false)
	private String btitle;
	
	@Column(nullable = false)
	private int bhits;
	
	@CreationTimestamp
	private Timestamp bdate;
	
	@UpdateTimestamp
	@Column(name = "up_bdate")
	private Timestamp upBdate;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@Lob
    @Column
    private String bcontent;
	
}
