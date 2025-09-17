package com.java.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameRun {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_run_seq")
    @SequenceGenerator(name = "game_run_seq", sequenceName = "GAME_RUN_SEQ", allocationSize = 1)
    @Column(name = "RUN_ID")
    private int runId;

    @Column(name = "USER_NAME", nullable = false, length = 255)
    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLUB_ID", referencedColumnName = "CLUB_ID")
    private GameResultClub club;

    @Column(name = "FINISHED_AT", nullable = false)
    private Timestamp finishedAt;

    @OneToMany(mappedBy = "gameRun", fetch = FetchType.LAZY)
    private List<ResultUnlocked> resultUnlockeds;
}
