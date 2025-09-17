package com.java.entity;

import com.java.entity.compositeId.ResponseId;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "RESULTUNLOCKED")
public class ResultUnlocked {

	@EmbeddedId
    private ResponseId id;  // run_id + question_id 복합키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private GameResultClub club;  

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finished_at", referencedColumnName = "finished_at", insertable = false, updatable = false)
    private GameRun gameRun;
    
}