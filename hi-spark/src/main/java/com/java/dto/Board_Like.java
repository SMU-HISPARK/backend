package com.java.dto;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.java.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board_Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bl_no; // PK (글 좋아요 번호)

    @ManyToOne
    @JoinColumn(name = "bno") // FK (글 번호)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id") // FK (아이디)
    private Member member;

    @CreationTimestamp
    private Timestamp bl_date; // 누른 날짜
}