package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.dto.Artist;
import com.java.dto.Chat;
import com.java.entity.Member;

public interface ChatRepository extends JpaRepository<Chat, Integer>{

	List<Chat> findByMemberAndArtistOrderByCreatedAtAsc(Member member, Artist artist);

}
