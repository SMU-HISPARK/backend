package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.userData.GameSession;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, String> {

}
