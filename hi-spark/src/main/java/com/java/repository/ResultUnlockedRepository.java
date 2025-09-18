package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.compositeId.UnlockedId;
import com.java.entity.userData.ResultUnlocked;

@Repository
public interface ResultUnlockedRepository extends JpaRepository<ResultUnlocked, UnlockedId> {

}
