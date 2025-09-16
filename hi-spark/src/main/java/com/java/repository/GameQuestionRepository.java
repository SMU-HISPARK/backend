package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.sourceData.GameQuestion;

@Repository
public interface GameQuestionRepository extends JpaRepository<GameQuestion, Integer> {

	List<GameQuestion> findByDayAndTime(Integer day, Integer time);

	
	
}
