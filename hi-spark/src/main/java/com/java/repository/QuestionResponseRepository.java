package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.compositeId.ResponseId;
import com.java.entity.sourceData.GameOptions;
import com.java.entity.userData.QuestionResponse;

@Repository
public interface QuestionResponseRepository extends JpaRepository<QuestionResponse, ResponseId> {

	Long countByOptions(GameOptions o);

}
