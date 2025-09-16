package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.dto.Comment;
import com.java.dto.Comment_Like;
import com.java.dto.Member;

@Repository
public interface Comment_LikeRepository extends JpaRepository<Comment_Like, Integer> {

	Optional<Comment_Like> findByCommentAndMember(Comment comment, Member member);

}
