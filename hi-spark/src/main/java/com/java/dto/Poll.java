package com.java.dto;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator; // SequenceGenerator import 추가
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
@Table(name = "poll")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POLL_SEQ") // IDENTITY -> SEQUENCE로 변경
    @SequenceGenerator(name = "POLL_SEQ", sequenceName = "POLL_SEQ", allocationSize = 1) // 시퀀스 제너레이터 추가
    private int poll_no; // PK (투표 번호)

    private String poll_title; // 투표 제목

    @Column(columnDefinition = "TEXT")
    private String poll_content; // 투표 내용

    @CreationTimestamp
    private Timestamp poll_start_date; // 시작 시각

    private Timestamp poll_end_date; // 종료 시각

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "login_id")
    private Member member; // FK (아이디)

    private String poll_file; // 첨부 파일
    
    @OneToMany(mappedBy = "poll")
    @Fetch(FetchMode.JOIN)
    @JsonManagedReference
    private List<Poll_Item> poll_items;

}