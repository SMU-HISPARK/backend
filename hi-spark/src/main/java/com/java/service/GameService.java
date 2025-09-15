package com.java.service;

import com.java.entity.sourceData.GameQuestion;

public interface GameService {

	GameQuestion findById(Integer question_id);

}
