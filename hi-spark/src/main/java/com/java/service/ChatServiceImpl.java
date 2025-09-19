package com.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dto.Artist;
import com.java.dto.Chat;
import com.java.entity.Member;
import com.java.repository.ChatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	@Autowired ChatRepository chatRepository;
	
    @Override
    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    @Override
    public List<Chat> findByMemberAndArtistOrderByCreatedAtAsc(Member member, Artist artist) {
        return chatRepository.findByMemberAndArtistOrderByCreatedAtAsc(member, artist);
    }



}