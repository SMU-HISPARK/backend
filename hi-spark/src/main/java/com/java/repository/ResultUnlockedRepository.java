package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.entity.compositeId.UnlockedId;
import com.java.entity.userData.ResultUnlocked;
import com.java.repository.projection.CountBucket;

@Repository
public interface ResultUnlockedRepository extends JpaRepository<ResultUnlocked, UnlockedId> {

	@Query("select count(distinct r.member) from ResultUnlocked r")
	Long CountDistinctMember();

	@Query(value = """
			select cnt, count(*) as members
			from (
				select member_id, count(*) as cnt
				from resultUnlocked
				group by member_id
				)
			where cnt between 1 and 5
			group by cnt
			order by cnt asc
			""", nativeQuery = true)
	List<CountBucket> countMembersByUnlockCount();

}
