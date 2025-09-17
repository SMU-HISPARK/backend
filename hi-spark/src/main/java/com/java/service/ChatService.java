package com.java.service;

import java.util.List;

import com.java.dto.Artist;
import com.java.dto.Chat;
import com.java.entity.Member;

public interface ChatService {

	void save(Chat chat);

	List<Chat> findByMemberAndArtistOrderByCreatedAtAsc(Member member, Artist artist);

}
