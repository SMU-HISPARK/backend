package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.sourceData.GameResultClub;
import com.java.entity.userData.GameRun;
import com.java.entity.userData.GameSession;

@Repository
public interface GameRunRepository extends JpaRepository<GameRun, Long> {

	List<GameRun> findByGameSessionAndResultClubOrderByFinishedAtAsc(GameSession gameSession, GameResultClub resultClub);

}
