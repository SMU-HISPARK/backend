package com.java.dto;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vote_Log {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VOTE_LOG_SEQ")
    @SequenceGenerator(name = "VOTE_LOG_SEQ", sequenceName = "VOTE_LOG_SEQ", allocationSize = 1)
    private int vote_no; // PK (투표 기록 번호)
    
    @ManyToOne
    @JoinColumn(name = "poll_no")
    private Poll poll; // FK (투표 번호)

    @ManyToOne
    @JoinColumn(name = "item_no")
    private Poll_Item pollItem; // FK (선택지 번호)

    @ManyToOne
    // member_id가 Member 엔티티의 login_id를 참조하도록 명시
    @JoinColumn(name = "member_id", referencedColumnName = "login_id") 
    private Member member; // FK (아이디)

    @CreationTimestamp
    private Timestamp vote_date; // 투표 시각
}