package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.sourceData.GameResultClub;

@Repository
public interface GameResultClubRepository extends JpaRepository<GameResultClub, Integer> {

}
