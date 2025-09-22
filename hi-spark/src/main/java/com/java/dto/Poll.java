package com.java.dto;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.java.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "poll")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POLL_SEQ")
    @SequenceGenerator(name = "POLL_SEQ", sequenceName = "POLL_SEQ", allocationSize = 1)
    @Column(name = "POLL_NO") // DB 컬럼명과 매핑
    private int poll_no; // PK (투표 번호)

    @Column(name = "POLL_TITLE") // DB 컬럼명과 매핑
    private String poll_title; // 투표 제목

    @Column(name = "POLL_CONTENT", columnDefinition = "CLOB") // DB 컬럼명과 매핑
    private String poll_content; // 투표 내용

    @CreationTimestamp
    @Column(name = "POLL_START_DATE") // DB 컬럼명과 매핑
    private Timestamp poll_start_date; // 시작 시각

    @Column(name = "POLL_END_DATE") // DB 컬럼명과 매핑
    private Timestamp poll_end_date; // 종료 시각

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID") // DB 컬럼명과 매핑
    private Member member; // FK (아이디)

    @Column(name = "POLL_FILE") // DB 컬럼명과 매핑
    private String poll_file; // 첨부 파일
    
    @OneToMany(mappedBy = "poll")
    @Fetch(FetchMode.JOIN)
    @JsonManagedReference
    private List<Poll_Item> poll_items;

}