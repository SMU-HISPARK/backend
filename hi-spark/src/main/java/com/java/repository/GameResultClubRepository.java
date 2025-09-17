package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.GameResultClub;

public interface GameResultClubRepository extends JpaRepository<GameResultClub, Integer>{

	List<GameResultClub> findAll();
}
