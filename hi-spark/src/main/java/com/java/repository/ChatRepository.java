package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.dto.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer>{

}
