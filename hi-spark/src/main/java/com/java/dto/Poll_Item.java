package com.java.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator; // import 추가
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Poll_Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POLL_ITEM_SEQ") // IDENTITY -> SEQUENCE로 변경
    @SequenceGenerator(name = "POLL_ITEM_SEQ", sequenceName = "POLL_ITEM_SEQ", allocationSize = 1) // 시퀀스 제너레이터 추가
    private int item_no; // PK (선택지 번호)

    private String item_content; // 선택지 내용
    
    @ManyToOne
    @JoinColumn(name = "poll_no")
    private Poll poll; // FK (투표 번호)
}