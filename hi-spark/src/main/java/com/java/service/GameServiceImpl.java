package com.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.entity.sourceData.GameQuestion;
import com.java.respository.GameQuestionRepository;

@Service
public class GameServiceImpl implements GameService {

	@Autowired GameQuestionRepository gqRep;
	
	@Override
	public GameQuestion findById(Integer question_id) {

		GameQuestion gq = gqRep.findById(question_id).orElse(null);
		
		return gq;
	}

	
	
}
