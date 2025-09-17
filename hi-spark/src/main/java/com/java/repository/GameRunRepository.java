package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.userData.GameRun;

@Repository
public interface GameRunRepository extends JpaRepository<GameRun, Integer> {

}
