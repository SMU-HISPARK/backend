package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.sourceData.GameOptions;

@Repository
public interface GameOptionsRepository extends JpaRepository<GameOptions, Integer>{

}
