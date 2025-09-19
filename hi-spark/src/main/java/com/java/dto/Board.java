package com.java.dto;


import java.sql.Timestamp;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.java.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "BOARD")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GEN")
	@SequenceGenerator(name = "BOARD_SEQ_GEN", sequenceName = "BOARD_SEQ", allocationSize = 1)
	private int bno; //PK
	
	private String btitle;
	
	@ColumnDefault("0")
	private int bhit;
	
	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp bdate;
	
	@Column(nullable = true, name="up_bdate")
	@UpdateTimestamp
	private Timestamp upBdate;
	
	@ManyToOne(fetch = FetchType.EAGER)   // eager - board/member 테이블 동시에 가져옴, lazy - 지연전략 : board 테이블만 가져오고 나중에 필요할 때 member테이블 가져옴
	@JoinColumn(name = "member_id")
	private Member member; //FK
	
	
	
	@Lob
	private String bcontent;
	
	@Column(nullable = true)
	private String bfile;
	
	@Column(name = "B_TYPE")
	@ColumnDefault("0")
	private int b_type;
	
	
	
}