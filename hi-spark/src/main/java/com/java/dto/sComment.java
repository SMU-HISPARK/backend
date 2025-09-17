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
import jakarta.persistence.Table;
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
@Table(name = "sComment")
public class sComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scno;
	
	@ManyToOne
    @JoinColumn(name = "bno")
    private Board board;
	
	@Column(columnDefinition = "TEXT")
	private String sccontent;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member; //FK
	
	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp scdate;
	
	@Column(nullable = true)
	@UpdateTimestamp
	private Timestamp up_scdate;
}