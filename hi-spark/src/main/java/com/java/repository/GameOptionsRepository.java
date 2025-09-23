package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.sourceData.GameOptions;
import com.java.entity.sourceData.GameQuestion;

@Repository
public interface GameOptionsRepository extends JpaRepository<GameOptions, Integer>{

	GameQuestion findQuestionByOptionId(Integer optionId);

}
