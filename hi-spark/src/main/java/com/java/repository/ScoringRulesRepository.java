package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.compositeId.ScoringRuleId;
import com.java.entity.sourceData.GameOptions;
import com.java.entity.sourceData.ScoringRules;

@Repository
public interface ScoringRulesRepository extends JpaRepository<ScoringRules, ScoringRuleId> {

	List<ScoringRules> findByOption(GameOptions gameOptions);

}
