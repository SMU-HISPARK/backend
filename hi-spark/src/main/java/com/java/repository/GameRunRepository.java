package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.GameRun;

public interface GameRunRepository extends JpaRepository<GameRun, Integer> {

	List<GameRun> findByMember_LoginId(String memberId);
}
